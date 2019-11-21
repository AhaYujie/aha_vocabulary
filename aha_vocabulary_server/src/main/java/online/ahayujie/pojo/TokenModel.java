package online.ahayujie.pojo;

public class TokenModel {

    private Long userId;    // 用户id
    private String token;   // 随机生成的uuid

    public TokenModel() {
    }

    public TokenModel(Long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "TokenModel{" +
                "userId=" + userId +
                ", token='" + token + '\'' +
                '}';
    }
}
