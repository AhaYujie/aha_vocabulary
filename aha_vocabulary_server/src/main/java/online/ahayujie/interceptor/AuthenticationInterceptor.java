package online.ahayujie.interceptor;

import online.ahayujie.pojo.TokenModel;
import online.ahayujie.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 * 无Token或者Session会被拦截
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final String HEADER_TOKEN_KEY = "token";

    private TokenService tokenService;

    @Autowired
    public AuthenticationInterceptor(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle");
        if (checkSession(request) || checkToken(request)) {
            return true;
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }

    /**
     * 检查token合法性，若合法则把userId添加到header
     * @param request
     * @return 合法返回true，否则返回false
     */
    private boolean checkToken(HttpServletRequest request) {
        String token = request.getHeader(HEADER_TOKEN_KEY);
        if (token == null) {
            return false;
        }
        TokenModel tokenModel = tokenService.getTokenModel(token);
        if (tokenModel == null) {
            return false;
        }
        if (!tokenService.checkToken(tokenModel)) {
            return false;
        }
        // 添加userId到request
        request.setAttribute("user_id", tokenModel.getUserId());
        return true;
    }

    private boolean checkSession(HttpServletRequest request) {
        // TODO:checkSession
        return false;
    }

}













