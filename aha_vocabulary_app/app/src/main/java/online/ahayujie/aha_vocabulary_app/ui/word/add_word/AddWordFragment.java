package online.ahayujie.aha_vocabulary_app.ui.word.add_word;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.goldze.mvvmhabit.base.BaseFragment;
import online.ahayujie.aha_vocabulary_app.BR;
import online.ahayujie.aha_vocabulary_app.R;
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
}
