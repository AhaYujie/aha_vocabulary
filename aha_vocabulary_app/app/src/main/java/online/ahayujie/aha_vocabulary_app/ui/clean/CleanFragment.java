package online.ahayujie.aha_vocabulary_app.ui.clean;

import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import me.goldze.mvvmhabit.base.BaseFragment;
import online.ahayujie.aha_vocabulary_app.BR;
import online.ahayujie.aha_vocabulary_app.R;
import online.ahayujie.aha_vocabulary_app.databinding.FragmentCleanBinding;

/**
 * 回收站Fragment
 */
public class CleanFragment extends BaseFragment<FragmentCleanBinding, CleanViewModel> {

    /**
     * 初始化根布局
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return 布局layout的id
     */
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container,
                               @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_clean;
    }

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    @Override
    public int initVariableId() {
        return BR.cleanViewModel;
    }
}
