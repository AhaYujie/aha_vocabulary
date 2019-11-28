package online.ahayujie.aha_vocabulary_app.ui.clean;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import online.ahayujie.aha_vocabulary_app.BR;
import online.ahayujie.aha_vocabulary_app.R;
import online.ahayujie.aha_vocabulary_app.data.DataRepository;

/**
 * 回收站ViewModel
 *
 * @author aha
 */
public class CleanViewModel extends BaseViewModel<DataRepository> {

    private ObservableList<CleanItemViewModel> cleanItemViewModels =
            new ObservableArrayList<>();

    private ItemBinding<CleanItemViewModel> cleanItemViewModelItemBinding =
            ItemBinding.of(BR.cleanItemViewModel, R.layout.item_clean);

    public CleanViewModel(@NonNull Application application, DataRepository dataRepository) {
        super(application, dataRepository);
        for (int i = 0; i < 20; i++) {
            cleanItemViewModels.add(new CleanItemViewModel(this));
        }
    }

    public ItemBinding<CleanItemViewModel> getCleanItemViewModelItemBinding() {
        return cleanItemViewModelItemBinding;
    }

    public ObservableList<CleanItemViewModel> getCleanItemViewModels() {
        return cleanItemViewModels;
    }
}
