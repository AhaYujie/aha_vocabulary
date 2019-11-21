package online.ahayujie.service.impl;

import online.ahayujie.dao.WordMapper;
import online.ahayujie.pojo.Word;
import online.ahayujie.service.TranslationService;
import online.ahayujie.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class WordServiceImpl implements WordService {

    private WordMapper wordMapper;

    private TranslationService translationService;

    @Autowired
    public WordServiceImpl(WordMapper wordMapper, TranslationService translationService) {
        this.wordMapper = wordMapper;
        this.translationService = translationService;
    }

    @Override
    public List<Word> getWordList(@Nullable Long userId, @Nullable Long wordClean, @Nullable Long page,
                                  @Nullable Long pageSize) {
        Map<String, Long> conditionMap = new HashMap<String, Long>();
        conditionMap.put("userId", userId);
        conditionMap.put("wordClean", wordClean);
        if (page != null && pageSize != null) {
            conditionMap.put("start", (page - 1) * pageSize);
            conditionMap.put("size", pageSize);
        }
        return wordMapper.get(conditionMap);
    }

    @Override
    public void updateWord(Word word) throws NoSuchElementException {
        if (wordMapper.update(word) != 1) {
            throw new NoSuchElementException("数据库没有该单词");
        }
    }

    @Override
    public void deleteWord(Long wordId) throws NoSuchElementException {
        if (wordMapper.delete(wordId) != 1) {
            throw new IllegalArgumentException("删除" + wordId + "单词失败");
        }
    }

    @Override
    public Word searchWord(String wordSpell) throws IOException, NullPointerException {
        String translation = translationService.translateWord(wordSpell);
        Word word = new Word();
        word.setWordSpell(wordSpell);
        word.setWordTranslation(translation);
        return word;
    }

    @Override
    public Word saveWord(String wordSpell, String wordTranslation, Long userId) throws IllegalArgumentException {
        Word word = new Word();
        word.setWordUserId(userId);
        word.setWordSpell(wordSpell);
        word.setWordTranslation(wordTranslation);
        word.setWordSearchTimes(1);
        word.setWordClean(1);
        word.setWordTime(new Date().toString());
        if (wordMapper.insert(word) != 1) {
            throw new IllegalArgumentException("添加单词错误");
        }
        return word;
    }
}












