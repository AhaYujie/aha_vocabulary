package online.ahayujie.aha_vocabulary_app.ui.word;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import online.ahayujie.aha_vocabulary_app.BR;
import online.ahayujie.aha_vocabulary_app.R;
import online.ahayujie.aha_vocabulary_app.app.MyApplication;
import online.ahayujie.aha_vocabulary_app.data.DataRepository;
import online.ahayujie.aha_vocabulary_app.data.bean.Word;
import online.ahayujie.aha_vocabulary_app.data.bean.WordListJson;
import online.ahayujie.aha_vocabulary_app.ui.word.search_word.SearchWordFragment;
import retrofit2.Response;

/**
 * 单词本ViewModel
 *
 * @author aha
 */
public class WordViewModel extends BaseViewModel<DataRepository> {

    private int currentPageIndex = 1;

    private ObservableList<WordItemViewModel> wordItemViewModels
            = new ObservableArrayList<>();

    private ItemBinding<WordItemViewModel> wordItemViewModelItemBinding
            = ItemBinding.of(BR.wordItemViewModel, R.layout.item_word);

    private SingleLiveEvent<Boolean> refreshLiveEvent = new SingleLiveEvent<>();

    private SingleLiveEvent<Boolean> loadMoreLiveEvent = new SingleLiveEvent<>();

    private SingleLiveEvent<Boolean> drawerLiveEvent = new SingleLiveEvent<>();

    private BindingCommand onRefreshCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // 下拉刷新
            Log.d(MyApplication.TAG, "下拉刷新");
            currentPageIndex = 1;
            getWordList();
        }
    });

    private BindingCommand onLoadMoreCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // 加载更多
            Log.d(MyApplication.TAG, "加载更多");
            currentPageIndex++;
            getWordList();
        }
    });

    private BindingCommand homeClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // 打开drawer
            drawerLiveEvent.setValue(true);
        }
    });

    private BindingCommand searchClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // 搜索新单词
            WordViewModel.this.startContainerActivity(SearchWordFragment.class.getCanonicalName());
        }
    });

    public WordViewModel(@NonNull Application application, DataRepository dataRepository) {
        super(application, dataRepository);
    }

    /**
     * 获取单词列表
     */
    void getWordList() {
        model.getWordList(currentPageIndex)
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(this)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        Log.d(MyApplication.TAG, "getWordList：开始加载: " + currentPageIndex);
                    }
                })
                .subscribe(new Consumer<Response<WordListJson>>() {
                    @Override
                    public void accept(Response<WordListJson> response) throws Exception {
                        Log.d(MyApplication.TAG, "getWordList：获取response");
                        if (response.code() == 401) {
                            // TODO:未登录
                            return;
                        }
                        else if (response.body() == null) {
                            throw new NullPointerException("WordListJson为null");
                        }
                        else if (response.body().getWordList().isEmpty()) {
                            ToastUtils.showShort("没有更多了");
                            currentPageIndex--;
                            return;
                        }
                        for (Word word : response.body().getWordList()) {
                            wordItemViewModels.add(new WordItemViewModel(WordViewModel.this, word));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(MyApplication.TAG, "getWordList：加载错误 " + throwable.getMessage());
                        refreshLiveEvent.setValue(false);
                        loadMoreLiveEvent.setValue(false);
                        ToastUtils.showShort("加载出错了...");
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d(MyApplication.TAG, "getWordList：加载完成");
                        refreshLiveEvent.setValue(false);
                        loadMoreLiveEvent.setValue(false);
                    }
                });
    }

    public BindingCommand getSearchClick() {
        return searchClick;
    }

    public BindingCommand getHomeClick() {
        return homeClick;
    }

    public SingleLiveEvent<Boolean> getDrawerLiveEvent() {
        return drawerLiveEvent;
    }

    public BindingCommand getOnLoadMoreCommand() {
        return onLoadMoreCommand;
    }

    public BindingCommand getOnRefreshCommand() {
        return onRefreshCommand;
    }

    public SingleLiveEvent<Boolean> getRefreshLiveEvent() {
        return refreshLiveEvent;
    }

    public SingleLiveEvent<Boolean> getLoadMoreLiveEvent() {
        return loadMoreLiveEvent;
    }

    public ItemBinding<WordItemViewModel> getWordItemViewModelItemBinding() {
        return wordItemViewModelItemBinding;
    }

    public ObservableList<WordItemViewModel> getWordItemViewModels() {
        return wordItemViewModels;
    }
}
