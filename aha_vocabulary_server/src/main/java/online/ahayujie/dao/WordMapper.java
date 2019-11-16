package online.ahayujie.dao;

import online.ahayujie.pojo.Word;

import java.util.List;
import java.util.Map;

public interface WordMapper {

    /**
     * 根据条件查询
     * @param conditionMap 可以包含 key：start(开始索引), size(页大小), userId, wordClean
     * @return
     */
    List<Word> get(Map<String, Integer> conditionMap);

    /**
     * 插入单词
     * @param word
     * @return
     */
    int insert(Word word);

    /**
     * 更新单词信息
     * 可以更新一个单词的一个或多个属性，除了 wordId 和 wordUserId
     * @param word
     * @return
     */
    int update(Word word);

}
