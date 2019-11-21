package online.ahayujie.dao;

import online.ahayujie.pojo.User;

public interface UserMapper {

    User getByName(String userName);

    User getById(Long userId);

    int insert(User user);

}
