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
import retrofit2.Response;

/**
 * 用户数据源实现类
 * @author aha
 */
public class UserDataSourceImpl implements UserDataSource {

    private static final String USER_FILE_NAME = "user";
    private static final String USER_TOKEN = "token";
    private static final String USER_NAME = "username";

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
        SharedPreferences pref = MyApplication.getContext()
                .getSharedPreferences(USER_FILE_NAME, Context.MODE_PRIVATE);
        return pref.getString(USER_TOKEN, "");
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
        deleteToken();
        editor.putString(USER_TOKEN, token);
        editor.apply();
    }

    /**
     * 删除token
     */
    @Override
    public void deleteToken() {
        SharedPreferences.Editor editor = MyApplication.getContext().
                getSharedPreferences(USER_FILE_NAME, Context.MODE_PRIVATE).edit();
        editor.remove(USER_TOKEN);
        editor.commit();
    }

    /**
     * 保存用户名
     *
     * @param userName
     */
    @Override
    public void saveUserName(String userName) {
        SharedPreferences.Editor editor = MyApplication.getContext().
                getSharedPreferences(USER_FILE_NAME, Context.MODE_PRIVATE).edit();
        deleteUserName();
        editor.putString(USER_NAME, userName);
        editor.apply();
    }

    /**
     * 获取用户名
     *
     * @return
     */
    @Override
    public String getUserName() {
        SharedPreferences pref = MyApplication.getContext()
                .getSharedPreferences(USER_FILE_NAME, Context.MODE_PRIVATE);
        return pref.getString(USER_NAME, "");
    }

    /**
     * 删除用户名
     */
    @Override
    public void deleteUserName() {
        SharedPreferences.Editor editor = MyApplication.getContext().
                getSharedPreferences(USER_FILE_NAME, Context.MODE_PRIVATE).edit();
        editor.remove(USER_NAME);
        editor.commit();
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
        saveUserName(userName);
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
    public Observable<Response<Void>> logout() {
        String token = getToken();
        deleteUserName();
        deleteToken();
        return userService.logout(token);
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
