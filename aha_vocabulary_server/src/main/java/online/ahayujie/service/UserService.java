package online.ahayujie.service;

import online.ahayujie.pojo.User;
import org.springframework.dao.DuplicateKeyException;

public interface UserService {

    /**
     * 新建用户
     * @param userName
     * @param password
     * @return
     * @throws DuplicateKeyException 用户名重复异常
     */
    User createUser(String userName, String password) throws DuplicateKeyException;

}
