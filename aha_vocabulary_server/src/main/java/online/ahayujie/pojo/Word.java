package online.ahayujie.pojo;

public class Word {

    private Long wordId;
    private Long wordUserId;            // 拥有这个单词的user的id
    private String wordSpell;           // 单词拼写
    private String wordTranslation;     // 单词翻译
    private String wordTime;            // 单词收录时间
    private Integer wordSearchTimes;    // 单词查询次数
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
