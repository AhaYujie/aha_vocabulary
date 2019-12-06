package online.ahayujie.aha_vocabulary_app.ui.login;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;

import online.ahayujie.aha_vocabulary_app.BR;
import online.ahayujie.aha_vocabulary_app.R;
import online.ahayujie.aha_vocabulary_app.app.MyApplication;
import online.ahayujie.aha_vocabulary_app.app.MyViewModelFactory;
import online.ahayujie.aha_vocabulary_app.databinding.ActivityLoginBinding;
import online.ahayujie.aha_vocabulary_app.ui.BaseAbstractActivity;
import online.ahayujie.aha_vocabulary_app.ui.main.MainActivity;

/**
 * 登录Activity
 *
 * @author aha
 */
public class LoginActivity extends BaseAbstractActivity<ActivityLoginBinding, LoginViewModel> {

    /**
     * 初始化根布局
     *
     * @param savedInstanceState
     * @return 布局layout的id
     */
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    @Override
    public int initVariableId() {
        return BR.loginViewModel;
    }

    @Override
    public LoginViewModel initViewModel() {
        MyViewModelFactory viewModelFactory = MyViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel.class);
    }

    @Override
    public void initViewObservable() {
        // 监听登录点击事件
        viewModel.getLoginLiveEvent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isClick) {
                if (isClick) {
                    viewModel.login(binding.loginUsernameEditText.getText().toString(),
                            binding.loginPasswordEditText.getText().toString());
                }
            }
        });
        // 监听登录状态事件
        viewModel.getLoginStatusLiveEvent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSuccess) {
                if (isSuccess) {
                    MainActivity.actionStart(LoginActivity.this);
                    LoginActivity.this.finish();
                    LoginActivity.this.viewModel.finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        // 退出应用
        Log.d(MyApplication.TAG, "退出应用");
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}









