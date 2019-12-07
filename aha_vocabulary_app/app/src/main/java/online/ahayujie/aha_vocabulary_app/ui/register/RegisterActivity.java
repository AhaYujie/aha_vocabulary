package online.ahayujie.aha_vocabulary_app.ui.register;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import online.ahayujie.aha_vocabulary_app.BR;
import online.ahayujie.aha_vocabulary_app.R;
import online.ahayujie.aha_vocabulary_app.app.MyViewModelFactory;
import online.ahayujie.aha_vocabulary_app.databinding.ActivityRegisterBinding;
import online.ahayujie.aha_vocabulary_app.ui.BaseAbstractActivity;

/**
 * 注册Activity
 *
 * @author aha
 */
public class RegisterActivity extends
        BaseAbstractActivity<ActivityRegisterBinding, RegisterViewModel> {

    private AlertDialog.Builder alertDialog;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    /**
     * 初始化根布局
     *
     * @param savedInstanceState
     * @return 布局layout的id
     */
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_register;
    }

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    @Override
    public int initVariableId() {
        return BR.registerViewModel;
    }

    @Override
    public RegisterViewModel initViewModel() {
        MyViewModelFactory viewModelFactory = MyViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, viewModelFactory).get(RegisterViewModel.class);
    }

    @Override
    public void initData() {
        initAlertDialog();
    }

    @Override
    public void initViewObservable() {
        // 监听注册点击
        viewModel.getRegisterLiveEvent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isClick) {
                if (isClick) {
                    viewModel.register(binding.registerUsernameEditText.getText().toString(),
                            binding.registerPasswordEditText.getText().toString(),
                            binding.registerPasswordEditTextSecond.getText().toString());
                }
            }
        });
        // 监听alert事件
        viewModel.getAlertLiveEvent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String alertMessage) {
                alertDialog.setMessage(alertMessage);
                alertDialog.show();
            }
        });
    }

    private void initAlertDialog() {
        alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("提示");
        alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
    }

}














