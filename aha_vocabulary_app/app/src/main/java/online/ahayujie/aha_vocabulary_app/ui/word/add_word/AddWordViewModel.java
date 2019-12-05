package online.ahayujie.aha_vocabulary_app.ui.word.add_word;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import online.ahayujie.aha_vocabulary_app.app.MyApplication;
import online.ahayujie.aha_vocabulary_app.data.DataRepository;
import online.ahayujie.aha_vocabulary_app.data.bean.WordJson;
import retrofit2.Response;

/**
 * 添加新单词viewModel
 *
 * @author aha
 */
public class AddWordViewModel extends BaseViewModel<DataRepository> {

    public static final String TOKEN_WORD_ADD = "token_word_add";

    private BindingCommand backClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // 返回
            finish();
        }
    });

    private BindingCommand saveClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // 保存新单词
            saveLiveEvent.setValue(true);
        }
    });

    private SingleLiveEvent<Boolean> saveLiveEvent = new SingleLiveEvent<>();

    public AddWordViewModel(@NonNull Application application, DataRepository dataRepository) {
        super(application, dataRepository);
    }

    public void saveWord(String wordSpell, String wordTranslation) {
        model.saveWord(wordSpell, wordTranslation)
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(this)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        Log.d(MyApplication.TAG, "开始保存新单词");
                    }
                })
                .subscribe(new Consumer<Response<WordJson>>() {
                    @Override
                    public void accept(Response<WordJson> response) throws Exception {
                        if (response.code() == 401) {
                            // TODO:未登录
                        }
                        if (response.body() == null) {
                            throw new NullPointerException("response body is null");
                        }
                        if (response.body().getStatus() == 0) {
                            throw new IllegalArgumentException("status is 0");
                        }
                        Log.d(MyApplication.TAG, "word: " + response.body().getWord());
                        Messenger.getDefault().send(response.body().getWord(), TOKEN_WORD_ADD);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(MyApplication.TAG, "捕获异常：" + throwable.getMessage());
                        ToastUtils.showShort("似乎出问题了...");
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d(MyApplication.TAG, "添加新单词完成");
                        AddWordViewModel.this.finish();
                    }
                });
    }

    public BindingCommand getSaveClick() {
        return saveClick;
    }

    public SingleLiveEvent<Boolean> getSaveLiveEvent() {
        return saveLiveEvent;
    }

    public BindingCommand getBackClick() {
        return backClick;
    }
}
