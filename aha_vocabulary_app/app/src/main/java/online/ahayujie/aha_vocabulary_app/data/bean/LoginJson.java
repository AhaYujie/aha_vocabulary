package online.ahayujie.aha_vocabulary_app.data.bean;

/**
 * 用户登录接口response json
 * @author aha
 */
public class LoginJson {

    /**
     * 0为登录失败，1为登录成功
     */
    private int status;

    private String token;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
