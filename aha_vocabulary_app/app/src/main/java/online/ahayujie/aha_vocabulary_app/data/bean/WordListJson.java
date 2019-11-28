package online.ahayujie.aha_vocabulary_app.data.bean;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 单词列表接口response json
 *
 * @author aha
 */
public class WordListJson {

    @SerializedName("word_list")
    private List<Word> wordList;

    public List<Word> getWordList() {
        return wordList;
    }

    public void setWordList(List<Word> wordList) {
        this.wordList = wordList;
    }

    @Override
    @NonNull
    public String toString() {
        return "WordListJson{" +
                "wordList=" + wordList +
                '}';
    }
}
