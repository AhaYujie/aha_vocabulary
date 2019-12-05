package online.ahayujie.aha_vocabulary_app.ui.word.add_word;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
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
import online.ahayujie.aha_vocabulary_app.databinding.FragmentAddWordBinding;

public class AddWordFragment extends BaseFragment<FragmentAddWordBinding, AddWordViewModel> {

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
        return R.layout.fragment_add_word;
    }

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    @Override
    public int initVariableId() {
        return BR.addWordViewModel;
    }

    @Override
    public AddWordViewModel initViewModel() {
        MyViewModelFactory viewModelFactory = MyViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, viewModelFactory).get(AddWordViewModel.class);
    }

    @Override
    public void initViewObservable() {
        // 监听保存按钮点击
        viewModel.getSaveLiveEvent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSave) {
                if (isSave) {
                    // 保存新单词
                    AddWordFragment.this.viewModel.saveWord(binding.addWordWordSpellEditText.
                            getText().toString(), binding.addWordWordTranslationEditText.getText()
                    .toString());
                }
                AddWordFragment.this.viewModel.finish();
            }
        });
    }
}













