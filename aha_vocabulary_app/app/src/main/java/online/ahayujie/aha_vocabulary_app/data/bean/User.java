package online.ahayujie.aha_vocabulary_app.data.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 用户bean类
 *
 * @author aha
 */
public class User {

    @SerializedName("user_id")
    private Long userId;

    @SerializedName("username")
    private String userName;

    @SerializedName("password")
    private String password;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
