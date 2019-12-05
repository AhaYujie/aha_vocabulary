package online.ahayujie.aha_vocabulary_app.ui.word.search_word;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.goldze.mvvmhabit.base.BaseFragment;
import online.ahayujie.aha_vocabulary_app.BR;
import online.ahayujie.aha_vocabulary_app.R;
import online.ahayujie.aha_vocabulary_app.app.MyViewModelFactory;
import online.ahayujie.aha_vocabulary_app.databinding.FragmentSearchWordBinding;


/**
 * 网络搜索新单词Fragment
 *
 * @author aha
 */
public class SearchWordFragment extends BaseFragment<FragmentSearchWordBinding, SearchWordViewModel> {

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
        return R.layout.fragment_search_word;
    }

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    @Override
    public int initVariableId() {
        return BR.searchWordViewModel;
    }

    @Override
    public void initData() {
        binding.searchWordSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                binding.searchWordCollectIcon.setClickable(true);
                binding.searchWordCollectIcon.setImageDrawable(SearchWordFragment.this.getResources()
                .getDrawable(R.mipmap.ic_collect_green));
                SearchWordFragment.this.viewModel.searchWord(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public SearchWordViewModel initViewModel() {
        MyViewModelFactory viewModelFactory = MyViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, viewModelFactory).get(SearchWordViewModel.class);
    }

    @Override
    public void initViewObservable() {
        // 监听收藏
        viewModel.getCollectLiveEvent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isCollect) {
                if (isCollect) {
                    binding.searchWordCollectIcon.setImageDrawable(SearchWordFragment.this.getResources()
                    .getDrawable(R.mipmap.ic_collected_green));
                    binding.searchWordCollectIcon.setClickable(false);
                }
            }
        });
    }
}













