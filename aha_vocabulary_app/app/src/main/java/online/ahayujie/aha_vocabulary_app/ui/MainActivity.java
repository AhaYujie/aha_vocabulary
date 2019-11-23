package online.ahayujie.aha_vocabulary_app.ui;

import android.os.Bundle;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.base.BaseViewModel;
import online.ahayujie.aha_vocabulary_app.BR;
import online.ahayujie.aha_vocabulary_app.R;
import online.ahayujie.aha_vocabulary_app.databinding.ActivityMainBinding;

/**
 * 主活动
 */
public class MainActivity extends BaseActivity<ActivityMainBinding, BaseViewModel> {

    /**
     * 初始化根布局
     *
     * @param savedInstanceState
     * @return 布局layout的id
     */
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    @Override
    public int initVariableId() {
        return online.ahayujie.aha_vocabulary_app.BR.mainViewModel;
    }
}
