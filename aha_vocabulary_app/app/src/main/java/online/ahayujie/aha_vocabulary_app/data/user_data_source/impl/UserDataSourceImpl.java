package online.ahayujie.aha_vocabulary_app.data.user_data_source.impl;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import online.ahayujie.aha_vocabulary_app.app.MyApplication;
import online.ahayujie.aha_vocabulary_app.data.bean.LoginJson;
import online.ahayujie.aha_vocabulary_app.data.bean.StatusJson;
import online.ahayujie.aha_vocabulary_app.data.bean.User;
import online.ahayujie.aha_vocabulary_app.data.user_data_source.UserDataSource;
import online.ahayujie.aha_vocabulary_app.data.user_data_source.network.api.UserService;

/**
 * 用户数据源实现类
 * @author aha
 */
public class UserDataSourceImpl implements UserDataSource {

    private static final String USER_FILE_NAME = "user";
    private static final String USER_TOKEN = "token";

    private UserService userService;

    private static UserDataSourceImpl INSTANCE = null;

    private UserDataSourceImpl(UserService userService) {
        this.userService = userService;
    }

    public static UserDataSource getInstance(UserService userService) {
        if (INSTANCE == null) {
            INSTANCE = new UserDataSourceImpl(userService);
        }
        return INSTANCE;
    }

    public static void detoryInstance() {
        INSTANCE = null;
    }

    /**
     * 获取token
     *
     * @return
     */
    @Override
    public String getToken() throws IllegalArgumentException {
//        SharedPreferences pref = MyApplication.getContext().getSharedPreferences(USER_FILE_NAME, Context.MODE_PRIVATE);
//        String token = pref.getString(USER_TOKEN, "");
//        if ("".equals(token)) {
//            throw new IllegalArgumentException("token为空");
//        }
        return "10_3c607b6518fe4a0baa0086572e918227";
    }

    /**
     * 保存token
     *
     * @param token
     */
    @Override
    public void saveToken(String token) {
        SharedPreferences.Editor editor = MyApplication.getContext().
                getSharedPreferences(USER_FILE_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(USER_TOKEN, token);
        editor.apply();
    }

    /**
     * 用户登录
     *
     * @param userName
     * @param password
     * @return
     */
    @Override
    public Observable<LoginJson> login(String userName, String password) {
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                new Gson().toJson(user));
        return userService.login(requestBody);
    }

    /**
     * 用户退出登录
     */
    @Override
    public Observable logout() {
        return userService.logout(getToken());
    }

    /**
     * 用户注册
     *
     * @param userName
     * @param password
     * @return
     */
    @Override
    public Observable<StatusJson> register(String userName, String password) {
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                new Gson().toJson(user));
        return userService.register(requestBody);
    }
}
