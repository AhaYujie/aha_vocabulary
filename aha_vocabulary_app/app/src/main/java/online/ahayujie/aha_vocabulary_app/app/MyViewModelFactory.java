package online.ahayujie.aha_vocabulary_app.app;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import online.ahayujie.aha_vocabulary_app.data.DataRepository;
import online.ahayujie.aha_vocabulary_app.data.user_data_source.UserDataSource;
import online.ahayujie.aha_vocabulary_app.data.user_data_source.impl.UserDataSourceImpl;
import online.ahayujie.aha_vocabulary_app.data.user_data_source.network.api.UserService;
import online.ahayujie.aha_vocabulary_app.data.word_data_source.WordDataSource;
import online.ahayujie.aha_vocabulary_app.data.word_data_source.impl.WordDataSourceImpl;
import online.ahayujie.aha_vocabulary_app.data.word_data_source.network.api.WordService;
import online.ahayujie.aha_vocabulary_app.ui.clean.CleanViewModel;
import online.ahayujie.aha_vocabulary_app.ui.login.LoginViewModel;
import online.ahayujie.aha_vocabulary_app.ui.main.MainViewModel;
import online.ahayujie.aha_vocabulary_app.ui.word.WordViewModel;
import online.ahayujie.aha_vocabulary_app.ui.word.add_word.AddWordViewModel;
import online.ahayujie.aha_vocabulary_app.ui.word.search_word.SearchWordViewModel;
import online.ahayujie.aha_vocabulary_app.util.RetrofitClient;

/**
 * ViewModel工厂类
 *
 * @author aha
 */
public class MyViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private static MyViewModelFactory INSTANCE;

    private final Application application;

    private final DataRepository dataRepository;

    private MyViewModelFactory(Application application) {
        this.application = application;
        UserService userService = RetrofitClient.getInstance().create(UserService.class);
        WordService wordService = RetrofitClient.getInstance().create(WordService.class);
        UserDataSource userDataSource = UserDataSourceImpl.getInstance(userService);
        WordDataSource wordDataSource = WordDataSourceImpl.getInstance(wordService, userDataSource);
        this.dataRepository = DataRepository.getInstance(userDataSource, wordDataSource);
    }

    public static MyViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            INSTANCE = new MyViewModelFactory(application);
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(WordViewModel.class)) {
            return (T) new WordViewModel(application, dataRepository);
        }
        else if (modelClass.isAssignableFrom(SearchWordViewModel.class)) {
            return (T) new SearchWordViewModel(application, dataRepository);
        }
        else if (modelClass.isAssignableFrom(AddWordViewModel.class)) {
            return (T) new AddWordViewModel(application, dataRepository);
        }
        else if (modelClass.isAssignableFrom(CleanViewModel.class)) {
            return (T) new CleanViewModel(application, dataRepository);
        }
        else if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(application, dataRepository);
        }
        else if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(application, dataRepository);
        }
        else {
            throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
        }
    }
}
