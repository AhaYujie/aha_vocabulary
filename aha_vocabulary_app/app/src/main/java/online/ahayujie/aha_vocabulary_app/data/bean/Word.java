package online.ahayujie.aha_vocabulary_app.data.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 单词bean类
 *
 * @author aha
 */
public class Word {

    @SerializedName("word_id")
    private int wordId;                 // 单词id

    @SerializedName("word_spell")
    private String wordSpell;           // 单词拼写

    @SerializedName("word_translation")
    private String wordTranslation;     // 单词翻译

    @SerializedName("word_time")
    private String wordTime;            // 单词收录时间

    @SerializedName("word_search_times")
    private Integer wordSearchTimes;        // 单词查询次数

    @SerializedName("word_clean")
    private Integer wordClean;              // 是否是被放入回收站的单词(0为放入的，1为不放入的)

    public int getWordId() {
        return wordId;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
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

    public String getWordTime() {
        return wordTime;
    }

    public void setWordTime(String wordTime) {
        this.wordTime = wordTime;
    }

    public Integer getWordSearchTimes() {
        return wordSearchTimes;
    }

    public void setWordSearchTimes(Integer wordSearchTimes) {
        this.wordSearchTimes = wordSearchTimes;
    }

    public Integer getWordClean() {
        return wordClean;
    }

    public void setWordClean(Integer wordClean) {
        this.wordClean = wordClean;
    }

    @Override
    public String toString() {
        return "Word{" +
                "wordId=" + wordId +
                ", wordSpell='" + wordSpell + '\'' +
                ", wordTranslation='" + wordTranslation + '\'' +
                ", wordTime='" + wordTime + '\'' +
                ", wordSearchTimes=" + wordSearchTimes +
                ", wordClean=" + wordClean +
                '}';
    }
}
