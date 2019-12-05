package online.ahayujie.aha_vocabulary_app.data.bean;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * 收录单词接口response json
 *
 * @author aha
 */
public class WordJson {

    /**
     *  0为收录失败，1为收录成功
     */
    private int status;

    private Word word;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    @Override
    @NonNull
    public String toString() {
        return "WordJson{" +
                "status=" + status +
                ", word=" + word +
                '}';
    }
}
