package online.ahayujie.controller;

import com.alibaba.fastjson.JSONObject;
import online.ahayujie.pojo.User;
import online.ahayujie.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthenticationController {

    private AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * 客户端token登录
     * @param user
     * @return
     */
    @PostMapping(value = "/token", produces = "application/json;charset=utf-8")
    public String tokenLogin(@RequestBody User user) {
        JSONObject jsonObject = new JSONObject();
        try {
            String token = authenticationService.tokenLogin(user);
            jsonObject.put("status", 1);
            jsonObject.put("token", token);
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
            jsonObject.put("status", 0);
        }
        return jsonObject.toJSONString();
    }

    /**
     * 客户端token退出登录
     * @param request
     * @param response
     */
    @DeleteMapping(value = "/token/logout")     // spring的Interceptor不能细分到http的method，无法拦截http method是delete的/token路径
    public void tokenLogout(HttpServletRequest request, HttpServletResponse response) {
        authenticationService.tokenLogout((Long) request.getAttribute("user_id"));
        response.setStatus(HttpServletResponse.SC_OK);
    }

}











