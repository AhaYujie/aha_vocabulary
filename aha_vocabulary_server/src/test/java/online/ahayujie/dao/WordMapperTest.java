package online.ahayujie.dao;

import online.ahayujie.pojo.Word;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class WordMapperTest {

    private static WordMapper wordMapper;

    @BeforeClass
    public static void beforeClass() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
        wordMapper = context.getBean(WordMapper.class);
    }

    @Test
    public void get() {
        Map<String, Long> conditionMap = new HashMap<String, Long>();
        conditionMap.put("userId", 1L);
        conditionMap.put("start", 0L);
        conditionMap.put("size", 10L);
        conditionMap.put("wordClean", 0L);
        System.out.println(wordMapper.get(conditionMap));
    }

    @Test
    public void insert() {
        Word word = new Word();
        word.setWordUserId(1L);
        word.setWordSpell("beach");
        word.setWordTranslation("沙滩");
        word.setWordTime(new Date().toString());
        word.setWordSearchTimes(1);
        word.setWordClean(0);
        assertEquals(1, wordMapper.insert(word));
    }

    @Test
    public void update() {
        Word word = new Word();
        word.setWordId(2L);
        word.setWordClean(0);
        word.setWordUserId(2L);
        word.setWordSpell("hello world");
        word.setWordTranslation("嗨 你的世界");
        wordMapper.update(word);
        get();
    }

}