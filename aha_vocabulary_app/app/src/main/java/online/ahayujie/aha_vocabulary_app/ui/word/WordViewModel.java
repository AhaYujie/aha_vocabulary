package online.ahayujie.aha_vocabulary_app.ui.word;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import online.ahayujie.aha_vocabulary_app.BR;
import online.ahayujie.aha_vocabulary_app.R;

/**
 * 单词本ViewModel
 */
public class WordViewModel extends BaseViewModel {

    private ObservableList<WordItemViewModel> wordItemViewModels
            = new ObservableArrayList<>();

    private ItemBinding<WordItemViewModel> wordItemViewModelItemBinding
            = ItemBinding.of(BR.wordItemViewModel, R.layout.item_word);

    public WordViewModel(@NonNull Application application) {
        super(application);
        for (int i = 0; i < 20; i++) {
            wordItemViewModels.add(new WordItemViewModel(this));
        }
    }

    public ItemBinding<WordItemViewModel> getWordItemViewModelItemBinding() {
        return wordItemViewModelItemBinding;
    }

    public ObservableList<WordItemViewModel> getWordItemViewModels() {
        return wordItemViewModels;
    }
}
