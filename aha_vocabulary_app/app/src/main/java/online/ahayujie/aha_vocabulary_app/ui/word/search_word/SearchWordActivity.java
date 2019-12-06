package online.ahayujie.aha_vocabulary_app.ui.word.search_word;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import online.ahayujie.aha_vocabulary_app.BR;
import online.ahayujie.aha_vocabulary_app.R;
import online.ahayujie.aha_vocabulary_app.app.MyViewModelFactory;
import online.ahayujie.aha_vocabulary_app.databinding.ActivitySearchWordBinding;
import online.ahayujie.aha_vocabulary_app.ui.BaseAbstractActivity;


/**
 * 网络搜索新单词Activity
 *
 * @author aha
 */
public class SearchWordActivity extends BaseAbstractActivity<ActivitySearchWordBinding, SearchWordViewModel> {

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SearchWordActivity.class);
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
        return R.layout.activity_search_word;
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
                binding.searchWordCollectIcon.setImageDrawable(SearchWordActivity.this.getResources()
                .getDrawable(R.mipmap.ic_collect_green));
                SearchWordActivity.this.viewModel.searchWord(query);
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
        MyViewModelFactory viewModelFactory = MyViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, viewModelFactory).get(SearchWordViewModel.class);
    }

    @Override
    public void initViewObservable() {
        // 监听收藏
        viewModel.getCollectLiveEvent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isCollect) {
                if (isCollect) {
                    binding.searchWordCollectIcon.setImageDrawable(SearchWordActivity.this.getResources()
                    .getDrawable(R.mipmap.ic_collected_green));
                    binding.searchWordCollectIcon.setClickable(false);
                }
            }
        });
    }
}













