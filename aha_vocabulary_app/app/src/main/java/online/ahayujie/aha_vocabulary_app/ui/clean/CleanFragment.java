package online.ahayujie.aha_vocabulary_app.ui.clean;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import online.ahayujie.aha_vocabulary_app.BR;
import online.ahayujie.aha_vocabulary_app.R;
import online.ahayujie.aha_vocabulary_app.app.MyViewModelFactory;
import online.ahayujie.aha_vocabulary_app.databinding.FragmentCleanBinding;
import online.ahayujie.aha_vocabulary_app.ui.main.MainActivity;

/**
 * 回收站Fragment
 *
 * @author aha
 */
public class CleanFragment extends BaseFragment<FragmentCleanBinding, CleanViewModel> {

    private AlertDialog.Builder alertDialog;

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

    @Override
    public CleanViewModel initViewModel() {
        MyViewModelFactory viewModelFactory = MyViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, viewModelFactory).get(CleanViewModel.class);
    }

    @Override
    public void initData() {
        binding.cleanMoreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0:
                        // 按字典顺序排序
                        viewModel.sortByWord();
                        break;
                    case 1:
                        // 按查询次数排序
                        viewModel.sortBySearchTimes();
                        break;
                    case 2:
                        // 按时间排序
                        viewModel.sortByTime();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("提示");
        alertDialog.setMessage("你确定要删除单词吗?");
        alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        binding.setCleanAdapter(new BindingRecyclerViewAdapter());
        viewModel.getCleanList();
    }

    @Override
    public void initViewObservable() {
        // 监听下拉加载
        viewModel.getRefreshLiveEvent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isRefresh) {
                if (isRefresh) {
                    binding.cleanRefreshLayout.startRefresh();
                }
                else {
                    binding.cleanRefreshLayout.finishRefreshing();
                }
            }
        });
        // 监听加载更多
        viewModel.getLoadMoreLiveEvent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoadMore) {
                if (isLoadMore) {
                    binding.cleanRefreshLayout.startLoadMore();
                }
                else {
                    binding.cleanRefreshLayout.finishLoadmore();
                }
            }
        });
        // 监听删除单词
        viewModel.getDeleteWordLiveEvent().observe(this, new Observer<CleanItemViewModel>() {
            @Override
            public void onChanged(final CleanItemViewModel cleanItemViewModel) {
                alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 确定删除单词
                        viewModel.deleteWord(cleanItemViewModel);
                    }
                });
                alertDialog.show();

            }
        });
        // 监听打开关闭drawer
        viewModel.getDrawerLiveEvent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isOpen) {
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity == null || mainActivity.getMainDrawerLayout() == null) {
                    return;
                }
                if (isOpen) {
                    mainActivity.getMainDrawerLayout().openDrawer(GravityCompat.START);
                }
                else {
                    mainActivity.getMainDrawerLayout().closeDrawer(GravityCompat.END);
                }
            }
        });
    }
}












