package online.ahayujie.aha_vocabulary_app.ui.clean;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
import online.ahayujie.aha_vocabulary_app.data.bean.StatusJson;
import online.ahayujie.aha_vocabulary_app.data.bean.Word;
import online.ahayujie.aha_vocabulary_app.data.bean.WordListJson;
import retrofit2.Response;

/**
 * 回收站ViewModel
 *
 * @author aha
 */
public class CleanViewModel extends BaseViewModel<DataRepository> {

    private int currentPageIndex = 1;

    private BindingCommand onRefreshCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // 下拉加载
            Log.d(MyApplication.TAG, "下拉加载");
            currentPageIndex = 1;
            cleanItemViewModels.clear();
            getCleanList();
        }
    });

    private BindingCommand onLoadMoreCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // 加载更多
            Log.d(MyApplication.TAG, "加载更多");
            currentPageIndex++;
            getCleanList();
        }
    });

    private BindingCommand homeClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // 打开drawer
            drawerLiveEvent.setValue(true);
        }
    });

    private SingleLiveEvent<Boolean> drawerLiveEvent = new SingleLiveEvent<>();

    private SingleLiveEvent<Boolean> refreshLiveEvent = new SingleLiveEvent<>();

    private SingleLiveEvent<Boolean> loadMoreLiveEvent = new SingleLiveEvent<>();

    private SingleLiveEvent<CleanItemViewModel> deleteWordLiveEvent = new SingleLiveEvent<>();

    private ObservableList<CleanItemViewModel> cleanItemViewModels =
            new ObservableArrayList<>();

    private ItemBinding<CleanItemViewModel> cleanItemViewModelItemBinding =
            ItemBinding.of(BR.cleanItemViewModel, R.layout.item_clean);

    public CleanViewModel(@NonNull Application application, DataRepository dataRepository) {
        super(application, dataRepository);
    }

    public void getCleanList() {
        model.getCleanList(currentPageIndex)
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(this)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        Log.d(MyApplication.TAG, "开始加载回收站列表：" + currentPageIndex);
                    }
                })
                .subscribe(new Consumer<Response<WordListJson>>() {
                    @Override
                    public void accept(Response<WordListJson> response) throws Exception {
                        if (response.code() == 401) {
                            // TODO:未登录
                        }
                        if (response.body() == null) {
                            throw new NullPointerException("response body is null");
                        }
                        if (response.body().getWordList().isEmpty()) {
                            ToastUtils.showShort("没有更多了");
                            currentPageIndex--;
                            return;
                        }
                        for (Word word : response.body().getWordList()) {
                            cleanItemViewModels.add(new CleanItemViewModel(CleanViewModel.this, word));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(MyApplication.TAG, "捕获异常：" + throwable.getMessage());
                        refreshLiveEvent.setValue(false);
                        loadMoreLiveEvent.setValue(false);
                        currentPageIndex--;
                        ToastUtils.showShort("似乎出问题了...");
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d(MyApplication.TAG, "加载回收站列表完成");
                        refreshLiveEvent.setValue(false);
                        loadMoreLiveEvent.setValue(false);
                    }
                });
    }

    public void deleteWord(final CleanItemViewModel cleanItemViewModel) {
        model.deleteWord(cleanItemViewModel.getWordId())
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(this)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        Log.d(MyApplication.TAG, "开始删除单词");
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
                        CleanViewModel.this.cleanItemViewModels.remove(cleanItemViewModel);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(MyApplication.TAG, "捕获异常: " + throwable.getMessage());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d(MyApplication.TAG, "删除单词完成");
                    }
                });
    }

    public void sortByWord() {
        List<CleanItemViewModel> tmp = new ArrayList<>(cleanItemViewModels);
        Collections.sort(tmp, new Comparator<CleanItemViewModel>() {
            @Override
            public int compare(CleanItemViewModel o1, CleanItemViewModel o2) {
                return o1.getWordSpell().get().compareTo(o2.getWordSpell().get());
            }
        });
        cleanItemViewModels.clear();
        cleanItemViewModels.addAll(tmp);
    }

    public void sortByTime() {
        List<CleanItemViewModel> tmp = new ArrayList<>(cleanItemViewModels);
        Collections.sort(tmp, new Comparator<CleanItemViewModel>() {
            @Override
            public int compare(CleanItemViewModel o1, CleanItemViewModel o2) {
                return o1.getWordTime().get().compareTo(o2.getWordTime().get());
            }
        });
        cleanItemViewModels.clear();
        cleanItemViewModels.addAll(tmp);
    }

    public void sortBySearchTimes() {
        List<CleanItemViewModel> tmp = new ArrayList<>(cleanItemViewModels);
        Collections.sort(tmp, new Comparator<CleanItemViewModel>() {
            @Override
            public int compare(CleanItemViewModel o1, CleanItemViewModel o2) {
                return o1.getWordSearchTimes().get().compareTo(o2.getWordSearchTimes().get());
            }
        });
        cleanItemViewModels.clear();
        cleanItemViewModels.addAll(tmp);
    }

    public BindingCommand getHomeClick() {
        return homeClick;
    }

    public SingleLiveEvent<Boolean> getDrawerLiveEvent() {
        return drawerLiveEvent;
    }

    public SingleLiveEvent<CleanItemViewModel> getDeleteWordLiveEvent() {
        return deleteWordLiveEvent;
    }

    public DataRepository getDataRepository() {
        return model;
    }

    public SingleLiveEvent<Boolean> getLoadMoreLiveEvent() {
        return loadMoreLiveEvent;
    }

    public SingleLiveEvent<Boolean> getRefreshLiveEvent() {
        return refreshLiveEvent;
    }

    public BindingCommand getOnRefreshCommand() {
        return onRefreshCommand;
    }

    public BindingCommand getOnLoadMoreCommand() {
        return onLoadMoreCommand;
    }

    public ItemBinding<CleanItemViewModel> getCleanItemViewModelItemBinding() {
        return cleanItemViewModelItemBinding;
    }

    public ObservableList<CleanItemViewModel> getCleanItemViewModels() {
        return cleanItemViewModels;
    }
}
