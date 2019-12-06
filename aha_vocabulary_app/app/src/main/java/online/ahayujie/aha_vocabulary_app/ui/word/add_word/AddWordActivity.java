package online.ahayujie.aha_vocabulary_app.ui.word.add_word;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import online.ahayujie.aha_vocabulary_app.BR;
import online.ahayujie.aha_vocabulary_app.R;
import online.ahayujie.aha_vocabulary_app.app.MyViewModelFactory;
import online.ahayujie.aha_vocabulary_app.databinding.ActivityAddWordBinding;
import online.ahayujie.aha_vocabulary_app.ui.BaseAbstractActivity;

/**
 * 添加新单词Activity
 *
 * @author aha
 */
public class AddWordActivity extends BaseAbstractActivity<ActivityAddWordBinding, AddWordViewModel> {

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, AddWordActivity.class);
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
        return R.layout.activity_add_word;
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
        MyViewModelFactory viewModelFactory = MyViewModelFactory.getInstance(getApplication());
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
                    AddWordActivity.this.viewModel.saveWord(binding.addWordWordSpellEditText.
                            getText().toString(), binding.addWordWordTranslationEditText.getText()
                    .toString());
                }
                AddWordActivity.this.viewModel.finish();
            }
        });
    }
}













