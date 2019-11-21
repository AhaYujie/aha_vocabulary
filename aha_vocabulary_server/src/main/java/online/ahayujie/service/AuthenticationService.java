package online.ahayujie.service;

import online.ahayujie.pojo.User;

public interface AuthenticationService {

    /**
     * 客户端token用户登录
     * @param user
     * @return 生成的token字符串
     * @throws IllegalArgumentException 密码错误或者用户名不存在
     */
    String tokenLogin(User user) throws IllegalArgumentException;

    /**
     * 客户端token用户退出登录
     * @param userId
     */
    void tokenLogout(Long userId);

}
