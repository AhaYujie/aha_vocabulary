package online.ahayujie.dao;

import online.ahayujie.pojo.User;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DuplicateKeyException;

import static org.junit.Assert.assertEquals;

public class UserMapperTest {

    private static UserMapper userMapper;

    @BeforeClass
    public static void beforeClass() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
        userMapper = context.getBean(UserMapper.class);
    }

    @Test
    public void getByName() {
    }

    @Test
    public void getById() {
    }

    @Test
    public void insert() {
        User user = new User();
        user.setUserName("aha");
        user.setPassword("123456");
        try {
            userMapper.insert(user);
        }
        catch (DuplicateKeyException e) {
            System.out.println("get error");
            e.printStackTrace();
        }
    }
}