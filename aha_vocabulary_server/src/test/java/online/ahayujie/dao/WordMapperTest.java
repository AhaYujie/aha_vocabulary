package online.ahayujie.dao;

import online.ahayujie.pojo.Word;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class WordMapperTest {

    @Test
    public void get() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
        WordMapper wordMapper = context.getBean(WordMapper.class);
        Map<String, Integer> conditionMap = new HashMap<String, Integer>();
        conditionMap.put("userId", 1);
        conditionMap.put("start", 0);
        conditionMap.put("size", 10);
        conditionMap.put("wordClean", 0);
        System.out.println(wordMapper.get(conditionMap));
    }

    @Test
    public void insert() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
        WordMapper wordMapper = context.getBean(WordMapper.class);
        Word word = new Word();
        System.out.println(word);
        word.setWordUserId(1L);
        word.setWordSpell("yujie");
        word.setWordTranslation("玉杰");
        word.setWordTime(new Date().toString());
        word.setWordSearchTimes(1);
        word.setWordClean(0);
        System.out.println(word);
        System.out.println(wordMapper);
        System.out.println(wordMapper.insert(word));
    }

    @Test
    public void update() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
        WordMapper wordMapper = context.getBean(WordMapper.class);
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