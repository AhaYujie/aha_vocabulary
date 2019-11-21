package online.ahayujie.controller;

import com.alibaba.fastjson.JSONObject;
import online.ahayujie.pojo.User;
import online.ahayujie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @PostMapping(produces = "application/json;charset=utf-8")
    public String register(@RequestBody User user) {
        System.out.println(user);
        JSONObject jsonObject = new JSONObject();
        try {
            userService.createUser(user.getUserName(), user.getPassword());
            jsonObject.put("status", 1);
        }
        catch (DuplicateKeyException e) {
            e.printStackTrace();
            jsonObject.put("status", 0);
        }
        return jsonObject.toJSONString();
    }

}
