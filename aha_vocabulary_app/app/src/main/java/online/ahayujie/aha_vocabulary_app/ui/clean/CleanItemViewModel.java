package online.ahayujie.aha_vocabulary_app.ui.clean;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import online.ahayujie.aha_vocabulary_app.app.MyApplication;
import online.ahayujie.aha_vocabulary_app.data.bean.StatusJson;
import online.ahayujie.aha_vocabulary_app.data.bean.Word;
import retrofit2.Response;

public class CleanItemViewModel extends ItemViewModel<CleanViewModel> {

    private int wordId;

    private ObservableField<String> wordSpell = new ObservableField<>();

    private ObservableField<String> wordTranslation = new ObservableField<>();

    private ObservableField<String> wordTime = new ObservableField<>();

    private ObservableField<String> wordSearchTimes = new ObservableField<>();

    private BindingCommand collectClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // 重新收录单词
            viewModel.getDataRepository().modifyWordClean(wordId, 1)
                    .compose(RxUtils.schedulersTransformer())
                    .compose(RxUtils.exceptionTransformer())
                    .doOnSubscribe(viewModel)
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            Log.d(MyApplication.TAG, "重新收录单词开始加载");
                        }
                    })
                    .subscribe(new Consumer<Response<StatusJson>>() {
                        @Override
                        public void accept(Response<StatusJson> response) throws Exception {
                            if (response.code() == 401) {
                                // 未登录
                                return;
                            }
                            if (response.body() == null) {
                                throw new NullPointerException("response body is null");
                            }
                            if (response.body().getStatus() == 0) {
                                throw new IllegalArgumentException("status is 0");
                            }
                            viewModel.getCleanItemViewModels().remove(CleanItemViewModel.this);
                            ToastUtils.showShort("重新收录单词成功");
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Log.d(MyApplication.TAG, "捕获异常: " + throwable.getMessage());
                            ToastUtils.showShort("似乎出问题了...");
                        }
                    }, new Action() {
                        @Override
                        public void run() throws Exception {
                            Log.d(MyApplication.TAG, "重新收录单词完成");
                        }
                    });
        }
    });

    private BindingCommand deleteClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // 删除单词
            viewModel.getDeleteWordLiveEvent().setValue(CleanItemViewModel.this);
        }
    });

    CleanItemViewModel(@NonNull CleanViewModel cleanViewModel, Word word) {
        super(cleanViewModel);
        wordSpell.set(word.getWordSpell());
        wordTranslation.set(word.getWordTranslation());
        wordTime.set(word.getWordTime());
        wordSearchTimes.set(String.valueOf(word.getWordSearchTimes()));
        wordId = word.getWordId();
    }

    public int getWordId() {
        return wordId;
    }

    public BindingCommand getCollectClick() {
        return collectClick;
    }

    public BindingCommand getDeleteClick() {
        return deleteClick;
    }

    public ObservableField<String> getWordSearchTimes() {
        return wordSearchTimes;
    }

    public ObservableField<String> getWordSpell() {
        return wordSpell;
    }

    public ObservableField<String> getWordTime() {
        return wordTime;
    }

    public ObservableField<String> getWordTranslation() {
        return wordTranslation;
    }
}
