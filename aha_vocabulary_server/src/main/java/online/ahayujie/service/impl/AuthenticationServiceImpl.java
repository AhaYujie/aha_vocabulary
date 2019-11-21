package online.ahayujie.service.impl;

import online.ahayujie.dao.UserMapper;
import online.ahayujie.pojo.TokenModel;
import online.ahayujie.pojo.User;
import online.ahayujie.service.AuthenticationService;
import online.ahayujie.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private UserMapper userMapper;
    private TokenService tokenService;

    @Autowired
    public AuthenticationServiceImpl(UserMapper userMapper, TokenService tokenService) {
        this.userMapper = userMapper;
        this.tokenService = tokenService;
    }

    @Override
    public String tokenLogin(User user) throws IllegalArgumentException {
        User userFound = userMapper.getByName(user.getUserName());
        if (userFound == null) {
            throw new IllegalArgumentException("用户名不存在");
        }
        if (!userFound.getPassword().equals(user.getPassword())) {
            throw new IllegalArgumentException("密码错误");
        }
        TokenModel tokenModel = tokenService.createToken(userFound.getUserId());
        return tokenService.getTokenString(tokenModel);
    }

    @Override
    public void tokenLogout(Long userId) {
        tokenService.deleteToken(userId);
    }

}
