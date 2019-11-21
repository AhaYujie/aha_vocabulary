package online.ahayujie.service.impl;

import online.ahayujie.pojo.TokenModel;
import online.ahayujie.service.TokenService;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

public class RedisTokenServiceImplTest {

    private static TokenService tokenService;

    private static TokenModel tokenModel;

    private static TokenModel tokenModelGet1;

    private static TokenModel tokenModelGet2;

    @BeforeClass
    public static void beforeClass() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
        tokenService = context.getBean(TokenService.class);
    }

    @Test
    public void Test() {
        createToken();
    }

    @Test
    @Ignore
    public void createToken() {
        Long userId = 2L;
        tokenModel = tokenService.createToken(userId);
        System.out.println("tokenModel : " + tokenModel);
        assertEquals(userId, tokenModel.getUserId());
    }

    @Test
    @Ignore
    public void getToken() {
        String token1 = tokenModel.getUserId() + "_" + tokenModel.getToken();
        String token2 = 2L + "_" + tokenModel.getToken();
        tokenModelGet1 = tokenService.getTokenModel(token1);
        tokenModelGet2 = tokenService.getTokenModel(token2);
        System.out.println("tokenModelGet1 : " + tokenModelGet1);
        System.out.println("tokenModelGet2 : " + tokenModelGet2);
    }

    @Test
    @Ignore
    public void checkToken() {
        assertTrue(tokenService.checkToken(tokenModel));
        assertTrue(tokenService.checkToken(tokenModelGet1));
        assertFalse(tokenService.checkToken(tokenModelGet2));
    }

    @Test
    @Ignore
    public void deleteToken() {
        tokenService.deleteToken(tokenModel.getUserId());
        assertFalse(tokenService.checkToken(tokenModel));
    }
}