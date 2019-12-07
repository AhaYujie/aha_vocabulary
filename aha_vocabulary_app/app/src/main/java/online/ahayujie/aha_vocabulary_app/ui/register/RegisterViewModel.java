package online.ahayujie.aha_vocabulary_app.ui.register;

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
import online.ahayujie.aha_vocabulary_app.data.bean.StatusJson;
import online.ahayujie.aha_vocabulary_app.utils.PasswordUtils;
import retrofit2.Response;

/**
 * 注册viewModel
 *
 * @author aha
 */
public class RegisterViewModel extends BaseViewModel<DataRepository> {

    private BindingCommand backClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // 返回
            RegisterViewModel.this.finish();
        }
    });

    private BindingCommand registerClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // 注册
            registerLiveEvent.setValue(true);
        }
    });

    private SingleLiveEvent<Boolean> registerLiveEvent = new SingleLiveEvent<>();

    private SingleLiveEvent<String> alertLiveEvent = new SingleLiveEvent<>();

    public RegisterViewModel(@NonNull Application application, DataRepository model) {
        super(application, model);
    }

    /**
     * 注册
     * @param userName 用户名
     * @param password1 第一次输入的密码
     * @param password2 第二次输入的密码
     */
    public void register(String userName, String password1, String password2) {
        if ("".equals(userName)) {
            alertLiveEvent.setValue("用户名不能为空");
            return;
        }
        else if ("".equals(password1) || "".equals(password2)) {
            alertLiveEvent.setValue("密码不能为空");
            return;
        }
        else if (!password1.equals(password2)) {
            alertLiveEvent.setValue("第一次输入的密码和第二次输入的密码不相同");
            return;
        }
        try {
            PasswordUtils.checkPassword(password1);
        }
        catch (IllegalArgumentException e) {
            alertLiveEvent.setValue(e.getMessage());
            return;
        }
        model.register(userName, password1)
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(this)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        Log.d(MyApplication.TAG, "开始注册");
                    }
                })
                .subscribe(new Consumer<StatusJson>() {
                    @Override
                    public void accept(StatusJson statusJson) throws Exception {
                        if (statusJson.getStatus() == 0) {
                            throw new IllegalArgumentException("status is 0");
                        }
                        ToastUtils.showShort("注册成功");
                        RegisterViewModel.this.finish();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(MyApplication.TAG, "注册异常：" + throwable.getMessage());
                        if (throwable instanceof IllegalArgumentException) {
                            alertLiveEvent.setValue("用户名已经存在");
                        }
                        else {
                            ToastUtils.showShort("似乎出问题了...");
                        }
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d(MyApplication.TAG, "注册完成");
                    }
                });
    }

    public SingleLiveEvent<String> getAlertLiveEvent() {
        return alertLiveEvent;
    }

    public SingleLiveEvent<Boolean> getRegisterLiveEvent() {
        return registerLiveEvent;
    }

    public BindingCommand getRegisterClick() {
        return registerClick;
    }

    public BindingCommand getBackClick() {
        return backClick;
    }
}








