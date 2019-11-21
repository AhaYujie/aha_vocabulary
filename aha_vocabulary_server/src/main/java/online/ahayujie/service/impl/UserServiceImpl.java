package online.ahayujie.service.impl;

import online.ahayujie.dao.UserMapper;
import online.ahayujie.pojo.User;
import online.ahayujie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User createUser(String userName, String password) throws DuplicateKeyException {
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        userMapper.insert(user);
        return user;
    }

}


