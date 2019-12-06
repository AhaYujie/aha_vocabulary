package online.ahayujie.aha_vocabulary_app.ui.word.search_word;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
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
import online.ahayujie.aha_vocabulary_app.data.bean.WordOnlineJson;
import retrofit2.Response;

import static online.ahayujie.aha_vocabulary_app.ui.word.add_word.AddWordViewModel.TOKEN_WORD_ADD;

/**
 * @author aha
 */
public class SearchWordViewModel extends BaseViewModel<DataRepository> {

    private ObservableField<Integer> wordVisibility = new ObservableField<>(View.GONE);

    private ObservableField<String> wordSpell = new ObservableField<>();

    private ObservableField<String> wordTranslation = new ObservableField<>();

    private SingleLiveEvent<Boolean> collectLiveEvent = new SingleLiveEvent<>();

    private BindingCommand collectClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // 收录新单词
            model.saveWord(wordSpell.get(), wordTranslation.get())
                    .compose(RxUtils.schedulersTransformer())
                    .compose(RxUtils.exceptionTransformer())
                    .doOnSubscribe(SearchWordViewModel.this)
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
                                // 未登录
                                return;
                            }
                            if (response.body() == null) {
                                throw new NullPointerException("response body is null");
                            }
                            if (response.body().getStatus() == 0) {
                                throw new IllegalArgumentException("status is 0");
                            }
                            Log.d(MyApplication.TAG, "word: " + response.body().getWord());
                            Messenger.getDefault().send(response.body().getWord(), TOKEN_WORD_ADD);
                            collectLiveEvent.setValue(true);
                            ToastUtils.showShort("收藏单词成功");
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
                        }
                    });
        }
    });

    private BindingCommand cancelClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // 取消搜索
            finish();
        }
    });

    public SearchWordViewModel(@NonNull Application application, DataRepository dataRepository) {
        super(application, dataRepository);
    }

    /**
     * 网络搜索新单词
     * @param query 查询的新单词
     */
    public void searchWord(String query) {
        model.searchWord(query)
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(this)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        Log.d(MyApplication.TAG, "搜索单词开始加载");
                        wordVisibility.set(View.GONE);
                        showDialog("正在搜索...");
                    }
                })
                .subscribe(new Consumer<Response<WordOnlineJson>>() {
                    @Override
                    public void accept(Response<WordOnlineJson> response) throws Exception {
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
                        wordSpell.set(response.body().getWordSpell());
                        wordTranslation.set(response.body().getWordTranslation());
                        wordVisibility.set(View.VISIBLE);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(MyApplication.TAG, "捕获异常：" + throwable.getMessage());
                        dismissDialog();
                        ToastUtils.showShort("似乎出问题了...");
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d(MyApplication.TAG, "搜索结束");
                        dismissDialog();
                    }
                });
    }

    public SingleLiveEvent<Boolean> getCollectLiveEvent() {
        return collectLiveEvent;
    }

    public ObservableField<String> getWordTranslation() {
        return wordTranslation;
    }

    public ObservableField<String> getWordSpell() {
        return wordSpell;
    }

    public BindingCommand getCollectClick() {
        return collectClick;
    }

    public BindingCommand getCancelClick() {
        return cancelClick;
    }

    public ObservableField<Integer> getWordVisibility() {
        return wordVisibility;
    }
}
