package online.ahayujie.service.impl;

import online.ahayujie.service.TranslationService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.IOException;
import java.util.UUID;

import static org.junit.Assert.*;

public class YouDaoTranslationServiceImplTest {

    private static TranslationService translationService;

    @BeforeClass
    public static void beforeClass() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
        translationService = context.getBean(TranslationService.class);
    }

    @Test
    public void translateWord() {
//        try {
//            translationService.translateWord("hello");
//        }
//        catch (IOException | NullPointerException e) {
//            e.printStackTrace();
//        }
    }

}