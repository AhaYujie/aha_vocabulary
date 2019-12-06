package online.ahayujie.aha_vocabulary_app.ui.main;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import online.ahayujie.aha_vocabulary_app.app.MyApplication;
import online.ahayujie.aha_vocabulary_app.data.DataRepository;
import retrofit2.Response;

/**
 * main viewModel
 *
 * @author aha
 */
public class MainViewModel extends BaseViewModel<DataRepository> {

    private SingleLiveEvent<Boolean> logoutStatusLiveEvent = new SingleLiveEvent<>();

    public MainViewModel(@NonNull Application application, DataRepository model) {
        super(application, model);
    }

    public void logout() {
        model.logout()
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(this)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        Log.d(MyApplication.TAG, "开始退出登录");
                    }
                })
                .subscribe(new Consumer<Response<Void>>() {
                    @Override
                    public void accept(Response<Void> response) throws Exception {
                        if (response.code() != 200) {
                            throw new IllegalArgumentException("response code:" + response.code());
                        }
                        logoutStatusLiveEvent.setValue(true);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(MyApplication.TAG, "退出登录异常：" + throwable.getMessage());
                        ToastUtils.showShort("似乎出问题了...");
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d(MyApplication.TAG, "退出登录完成");
                    }
                });
    }

    public DataRepository getDataRepository() {
        return model;
    }

    public SingleLiveEvent<Boolean> getLogoutStatusLiveEvent() {
        return logoutStatusLiveEvent;
    }

}
