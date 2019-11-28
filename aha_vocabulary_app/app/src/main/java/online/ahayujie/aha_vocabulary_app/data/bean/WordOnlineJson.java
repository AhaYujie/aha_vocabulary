package online.ahayujie.aha_vocabulary_app.data.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 搜索网络单词接口response json
 */
public class WordOnlineJson {

    private int status; // 0为搜索失败，1为搜索成功

    @SerializedName("word_spell")
    private String wordSpell;   // 单词拼写

    @SerializedName("word_translation")
    private String wordTranslation; // 单词翻译

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getWordSpell() {
        return wordSpell;
    }

    public void setWordSpell(String wordSpell) {
        this.wordSpell = wordSpell;
    }

    public String getWordTranslation() {
        return wordTranslation;
    }

    public void setWordTranslation(String wordTranslation) {
        this.wordTranslation = wordTranslation;
    }

    @Override
    public String toString() {
        return "WordOnlineJson{" +
                "status=" + status +
                ", wordSpell='" + wordSpell + '\'' +
                ", wordTranslation='" + wordTranslation + '\'' +
                '}';
    }
}
