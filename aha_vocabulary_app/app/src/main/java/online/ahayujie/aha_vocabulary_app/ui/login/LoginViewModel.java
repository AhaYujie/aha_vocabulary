package online.ahayujie.aha_vocabulary_app.ui.login;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

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
import online.ahayujie.aha_vocabulary_app.data.bean.LoginJson;

/**
 * 登录ViewModel
 *
 * @author aha
 */
public class LoginViewModel extends BaseViewModel<DataRepository> {

    private BindingCommand clickLogin = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // 登录
            loginLiveEvent.setValue(true);
        }
    });

    private BindingCommand clickRegister = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // TODO:注册
        }
    });

    private SingleLiveEvent<Boolean> loginLiveEvent = new SingleLiveEvent<>();

    private SingleLiveEvent<Boolean> loginStatusLiveEvent = new SingleLiveEvent<>();

    public LoginViewModel(@NonNull Application application, DataRepository model) {
        super(application, model);
    }

    public void login(String userName, String password) {
        model.login(userName, password)
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(this)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        Log.d(MyApplication.TAG, "开始登录");
                        showDialog("正在登录...");
                    }
                })
                .subscribe(new Consumer<LoginJson>() {
                    @Override
                    public void accept(LoginJson loginJson) throws Exception {
                        if (loginJson.getStatus() == 0) {
                            throw new IllegalArgumentException("status is 0");
                        }
                        model.saveToken(loginJson.getToken());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(MyApplication.TAG, "登录异常：" + throwable.getMessage());
                        dismissDialog();
                        if (throwable instanceof IllegalArgumentException) {
                            ToastUtils.showShort("用户名不存在或密码错误!");
                        }
                        else {
                            ToastUtils.showShort("似乎出问题了...");
                        }
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d(MyApplication.TAG, "登录成功");
                        dismissDialog();
                        loginStatusLiveEvent.setValue(true);
                    }
                });
    }

    public SingleLiveEvent<Boolean> getLoginStatusLiveEvent() {
        return loginStatusLiveEvent;
    }

    public BindingCommand getClickRegister() {
        return clickRegister;
    }

    public BindingCommand getClickLogin() {
        return clickLogin;
    }

    public SingleLiveEvent<Boolean> getLoginLiveEvent() {
        return loginLiveEvent;
    }
}
