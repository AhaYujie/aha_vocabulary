package online.ahayujie.aha_vocabulary_app.ui.word.search_word;

import android.app.Application;

import androidx.annotation.NonNull;

import me.goldze.mvvmhabit.base.BaseViewModel;
import online.ahayujie.aha_vocabulary_app.data.DataRepository;

/**
 * @author aha
 */
public class SearchWordViewModel extends BaseViewModel<DataRepository> {

    public SearchWordViewModel(@NonNull Application application, DataRepository dataRepository) {
        super(application, dataRepository);
    }

}
