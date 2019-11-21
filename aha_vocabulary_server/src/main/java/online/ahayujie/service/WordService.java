package online.ahayujie.service;

import online.ahayujie.pojo.Word;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

public interface WordService {

    /**
     * 获取单词列表
     * userId, wordClean，page和pageSize为条件，若无条件约束则传入null
     * @param userId
     * @param wordClean
     * @param page
     * @param pageSize
     * @return
     */
    List<Word> getWordList(@Nullable Long userId, @Nullable Long wordClean, @Nullable Long page, @Nullable Long pageSize);

    /**
     * 更新单词信息
     * @param word
     * @return 更新成功返回true，否则返回false
     */
    void updateWord(Word word) throws NoSuchElementException;

    /**
     * 删除单词
     * @param wordId 被删除的单词id
     * @return
     */
    void deleteWord(Long wordId) throws NoSuchElementException;

    /**
     * 网络搜索新单词
     * @param wordSpell
     * @return
     */
    Word searchWord(String wordSpell) throws IOException, NullPointerException;

    /**
     * 收录新单词
     * @param wordSpell
     * @param wordTranslation
     * @param userId 单词的拥有者id
     */
    Word saveWord(String wordSpell, String wordTranslation, Long userId) throws IllegalArgumentException;

}
