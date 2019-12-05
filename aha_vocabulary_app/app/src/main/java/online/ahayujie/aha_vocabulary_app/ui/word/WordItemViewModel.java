package online.ahayujie.aha_vocabulary_app.ui.word;

import android.util.Log;
import android.view.View;

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

/**
 * 单词item ViewModel
 *
 * @author aha
 */
public class WordItemViewModel extends ItemViewModel<WordViewModel> {

    private int wordId;

    private ObservableField<String> wordSpell = new ObservableField<>();

    private ObservableField<String> wordTranslation = new ObservableField<>();

    private ObservableField<String> wordTime = new ObservableField<>();

    private ObservableField<String> wordSearchTimes = new ObservableField<>();

    private ObservableField<Integer> wordSpellHideVisibility = new ObservableField<>(View.GONE);

    private ObservableField<Integer> wordTranslationHideVisibility = new ObservableField<>(View.GONE);

    private BindingCommand wordSpellHideClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // 显示单词拼写
            wordSpellHideVisibility.set(View.GONE);
        }
    });

    private BindingCommand wordTranslationHideClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // 显示单词翻译
            wordTranslationHideVisibility.set(View.GONE);
        }
    });

    private BindingCommand wordCleanClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // TODO:将单词放入回收站
            viewModel.getDataRepository().modifyWordClean(wordId, 0)
                    .compose(RxUtils.schedulersTransformer())
                    .compose(RxUtils.exceptionTransformer())
                    .doOnSubscribe(viewModel)
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            Log.d(MyApplication.TAG, "开始把单词放入回收站");
                        }
                    })
                    .subscribe(new Consumer<Response<StatusJson>>() {
                        @Override
                        public void accept(Response<StatusJson> response) throws Exception {
                            if (response.code() == 401) {
                                // TODO:未登录
                            }
                            if (response.body() == null) {
                                throw new NullPointerException("response body is null");
                            }
                            if (response.body().getStatus() == 0) {
                                throw new IllegalArgumentException("status is 0");
                            }
                            viewModel.getWordItemViewModels().remove(WordItemViewModel.this);
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
                            Log.d(MyApplication.TAG, "把单词放入回收站完成");
                        }
                    });
        }
    });

    private BindingCommand wordAddClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // 增加单词查询次数
            Log.d(MyApplication.TAG, "增加单词查询次数");
            modifyWordSearchTimes(1);
        }
    });

    private BindingCommand wordReduceClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // 减少单词查询次数
            Log.d(MyApplication.TAG, "减少单词查询次数");
            modifyWordSearchTimes(-1);
        }
    });

    WordItemViewModel(@NonNull WordViewModel wordViewModel, Word word) {
        super(wordViewModel);
        wordSpell.set(word.getWordSpell());
        wordTranslation.set(word.getWordTranslation());
        wordTime.set(word.getWordTime());
        wordSearchTimes.set(String.valueOf(word.getWordSearchTimes()));
        wordId = word.getWordId();
    }

    /**
     * 修改单词查询次数
     * @param wordSearchTimesModified 单词查询次数修改量
     */
    private void modifyWordSearchTimes(final int wordSearchTimesModified) {
        viewModel.getDataRepository().modifyWordSearchTimes(wordId,
                Integer.parseInt(wordSearchTimes.get()) + wordSearchTimesModified)
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(viewModel)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        Log.d(MyApplication.TAG, "开始改变单词查询次数");
                    }
                })
                .subscribe(new Consumer<Response<StatusJson>>() {
                    @Override
                    public void accept(Response<StatusJson> response) throws Exception {
                        if (response.code() == 401) {
                            // TODO:未登录
                            Log.d(MyApplication.TAG, "401未登录");
                        }
                        if (response.body() == null) {
                            throw new NullPointerException("response body 为null");
                        }
                        if (response.body().getStatus() == 0) {
                            throw new IllegalArgumentException("status为0");
                        }
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
                        Log.d(MyApplication.TAG, "修改单词查询次数完成");
                        wordSearchTimes.set(String.valueOf(Integer.parseInt(wordSearchTimes.get()) + wordSearchTimesModified));
                    }
                });
    }

    public BindingCommand getWordSpellHideClick() {
        return wordSpellHideClick;
    }

    public BindingCommand getWordTranslationHideClick() {
        return wordTranslationHideClick;
    }

    public ObservableField<Integer> getWordSpellHideVisibility() {
        return wordSpellHideVisibility;
    }

    public ObservableField<Integer> getWordTranslationHideVisibility() {
        return wordTranslationHideVisibility;
    }

    public BindingCommand getWordAddClick() {
        return wordAddClick;
    }

    public BindingCommand getWordReduceClick() {
        return wordReduceClick;
    }

    public ObservableField<String> getWordSearchTimes() {
        return wordSearchTimes;
    }

    public BindingCommand getWordCleanClick() {
        return wordCleanClick;
    }

    public ObservableField<String> getWordTime() {
        return wordTime;
    }

    public ObservableField<String> getWordSpell() {
        return wordSpell;
    }

    public ObservableField<String> getWordTranslation() {
        return wordTranslation;
    }
}
