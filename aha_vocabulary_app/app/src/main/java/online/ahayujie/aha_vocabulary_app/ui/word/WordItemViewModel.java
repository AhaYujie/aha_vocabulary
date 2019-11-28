package online.ahayujie.aha_vocabulary_app.ui.word;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import online.ahayujie.aha_vocabulary_app.data.bean.Word;

/**
 * @author aha
 */
public class WordItemViewModel extends ItemViewModel<WordViewModel> {

    private int wordId;

    private ObservableField<String> wordSpell = new ObservableField<>();

    private ObservableField<String> wordTranslation = new ObservableField<>();

    private ObservableField<String> wordTime = new ObservableField<>();

    private ObservableField<String> wordSearchTimes = new ObservableField<>();

    private BindingCommand wordCleanClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // TODO:将单词放入回收站
        }
    });

    private BindingCommand wordAddClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // TODO:增加单词查询次数
        }
    });

    private BindingCommand wordReduceClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // TODO:减少单词查询次数
        }
    });

    WordItemViewModel(@NonNull WordViewModel wordViewModel, Word word) {
        super(wordViewModel);
        wordSpell.set(word.getWordSpell());
        wordTranslation.set(word.getWordTranslation());
        wordTime.set(word.getWordTime());
        wordSearchTimes.set(String.valueOf(word.getWordSearchTimes()));
        wordId = word.getWordId();
    }

    public BindingCommand getWordAddClick() {
        return wordAddClick;
    }

    public BindingCommand getWordReduceClick() {
        return wordReduceClick;
    }

    public ObservableField<String> getWordSearchTimes() {
        return wordSearchTimes;
    }

    public BindingCommand getWordCleanClick() {
        return wordCleanClick;
    }

    public ObservableField<String> getWordTime() {
        return wordTime;
    }

    public ObservableField<String> getWordSpell() {
        return wordSpell;
    }

    public ObservableField<String> getWordTranslation() {
        return wordTranslation;
    }
}
