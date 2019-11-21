package online.ahayujie.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Word {

    @JsonProperty("word_id")
    private Long wordId;
    @JsonProperty("word_user_id")
    private Long wordUserId;            // 拥有这个单词的user的id
    @JsonProperty("word_spell")
    private String wordSpell;           // 单词拼写
    @JsonProperty("word_translation")
    private String wordTranslation;     // 单词翻译
    @JsonProperty("word_time")
    private String wordTime;            // 单词收录时间
    @JsonProperty("word_search_times")
    private Integer wordSearchTimes;    // 单词查询次数
    @JsonProperty("word_clean")
    private Integer wordClean;          // 单词是否被放入回收站，0为放入回收站，1为没有放入

    public Long getWordId() {
        return wordId;
    }

    public void setWordId(Long wordId) {
        this.wordId = wordId;
    }

    public Long getWordUserId() {
        return wordUserId;
    }

    public void setWordUserId(Long wordUserId) {
        this.wordUserId = wordUserId;
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
                ", wordUserId=" + wordUserId +
                ", wordSpell='" + wordSpell + '\'' +
                ", wordTranslation='" + wordTranslation + '\'' +
                ", wordTime='" + wordTime + '\'' +
                ", wordSearchTimes=" + wordSearchTimes +
                ", wordClean=" + wordClean +
                '}';
    }
}
