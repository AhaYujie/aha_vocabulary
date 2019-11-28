package online.ahayujie.aha_vocabulary_app.ui.word;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import me.goldze.mvvmhabit.base.BaseFragment;
import online.ahayujie.aha_vocabulary_app.BR;
import online.ahayujie.aha_vocabulary_app.R;
import online.ahayujie.aha_vocabulary_app.databinding.FragmentWordBinding;
import online.ahayujie.aha_vocabulary_app.ui.MainActivity;

/**
 * 单词本Fragment
 *
 * @author aha
 */
public class WordFragment extends BaseFragment<FragmentWordBinding, WordViewModel> {

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
        return R.layout.fragment_word;
    }

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    @Override
    public int initVariableId() {
        return BR.wordViewModel;
    }

    @Override
    public void initData() {
        viewModel.getWordList();
    }

    @Override
    public void initViewObservable() {
        // 监听drawer打开关闭
        viewModel.getDrawerLiveEvent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean flag) {
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity == null || mainActivity.getMainDrawerLayout() == null) {
                    return;
                }
                if (flag) {
                    mainActivity.getMainDrawerLayout().openDrawer(GravityCompat.START);
                }
                else {
                    mainActivity.getMainDrawerLayout().closeDrawer(GravityCompat.END);
                }
            }
        });
        // 监听下拉刷新
        viewModel.getRefreshLiveEvent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean flag) {
                if (flag) {
                    binding.wordRefreshLayout.startRefresh();
                }
                else {
                    binding.wordRefreshLayout.finishRefreshing();
                }
            }
        });
        // 监听加载更多
        viewModel.getLoadMoreLiveEvent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean flag) {
                if (flag) {
                    binding.wordRefreshLayout.startLoadMore();
                }
                else {
                    binding.wordRefreshLayout.finishLoadmore();
                }
            }
        });
    }
}











