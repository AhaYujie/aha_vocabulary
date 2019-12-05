package online.ahayujie.aha_vocabulary_app.ui.word;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import online.ahayujie.aha_vocabulary_app.BR;
import online.ahayujie.aha_vocabulary_app.R;
import online.ahayujie.aha_vocabulary_app.app.MyApplication;
import online.ahayujie.aha_vocabulary_app.app.MyViewModelFactory;
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
    public WordViewModel initViewModel() {
        MyViewModelFactory viewModelFactory = MyViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, viewModelFactory).get(WordViewModel.class);
    }

    @Override
    public void initData() {
        binding.wordMoreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0:
                        // 按字典顺序排序
                        Log.d(MyApplication.TAG, "按字典顺序排序");
                        viewModel.sortByWord();
                        break;
                    case 1:
                        // 按查询次数排序
                        Log.d(MyApplication.TAG, "按查询次数排序");
                        viewModel.sortBySearchTimes();
                        break;
                    case 2:
                        // 按收录时间排序
                        Log.d(MyApplication.TAG, "按收录时间排序");
                        viewModel.sortByTime();
                        break;
                    case 3:
                        // 隐藏英文
                        Log.d(MyApplication.TAG, "隐藏英文");
                        for (WordItemViewModel wordItemViewModel : viewModel.getWordItemViewModels()) {
                            wordItemViewModel.getWordSpellHideVisibility().set(View.VISIBLE);
                        }
                        break;
                    case 4:
                        // 隐藏中文
                        Log.d(MyApplication.TAG, "隐藏中文");
                        for (WordItemViewModel wordItemViewModel : viewModel.getWordItemViewModels()) {
                            wordItemViewModel.getWordTranslationHideVisibility().set(View.VISIBLE);
                        }
                        break;
                    case 5:
                        // 显示英文
                        Log.d(MyApplication.TAG, "显示英文");
                        for (WordItemViewModel wordItemViewModel : viewModel.getWordItemViewModels()) {
                            wordItemViewModel.getWordSpellHideVisibility().set(View.GONE);
                        }
                        break;
                    case 6:
                        // 显示中文
                        Log.d(MyApplication.TAG, "显示中文");
                        for (WordItemViewModel wordItemViewModel : viewModel.getWordItemViewModels()) {
                            wordItemViewModel.getWordTranslationHideVisibility().set(View.GONE);
                        }
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        binding.setWordAdapter(new BindingRecyclerViewAdapter());
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











