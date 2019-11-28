package online.ahayujie.aha_vocabulary_app.ui.word.add_word;

import android.app.Application;

import androidx.annotation.NonNull;

import me.goldze.mvvmhabit.base.BaseViewModel;
import online.ahayujie.aha_vocabulary_app.data.DataRepository;

/**
 * @author aha
 */
public class AddWordViewModel extends BaseViewModel<DataRepository> {

    public AddWordViewModel(@NonNull Application application, DataRepository dataRepository) {
        super(application, dataRepository);
    }

}
