package online.ahayujie.aha_vocabulary_app.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.ViewDataBinding;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.base.BaseViewModel;
import online.ahayujie.aha_vocabulary_app.app.MyApplication;

/**
 * 活动基类
 *
 * @author aha
 */
public abstract class BaseAbstractActivity<V extends ViewDataBinding, VM extends BaseViewModel>
        extends BaseActivity<V, VM> {

    private AlertDialog.Builder loginDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyApplication.addActivity(this);
        initLoginDialog();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        MyApplication.deleteActivity(this);
        super.onDestroy();
    }

    private void initLoginDialog() {
        loginDialog = new AlertDialog.Builder(this);
        loginDialog.setTitle("提示");
        loginDialog.setMessage("尚未登录, 请先登录后使用完整功能");
    }

    public void showLoginDialog(String buttonText, DialogInterface.OnClickListener onClickListener) {
        loginDialog.setPositiveButton(buttonText, onClickListener);
        loginDialog.show();
    }

}
