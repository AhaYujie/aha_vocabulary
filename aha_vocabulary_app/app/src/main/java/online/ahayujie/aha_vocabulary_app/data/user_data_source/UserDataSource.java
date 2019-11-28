package online.ahayujie.aha_vocabulary_app.data.user_data_source;

import io.reactivex.Observable;
import online.ahayujie.aha_vocabulary_app.data.bean.LoginJson;
import online.ahayujie.aha_vocabulary_app.data.bean.StatusJson;

/**
 * 用户数据源接口
 */
public interface UserDataSource {

    /**
     * 获取token
     * @return
     */
    String getToken();

    /**
     * 保存token
     * @param token
     */
    void saveToken(String token) throws IllegalArgumentException;

    /**
     * 用户登录
     * @param userName
     * @param password
     * @return
     */
    Observable<LoginJson> login(String userName, String password);

    /**
     * 用户退出登录
     */
    Observable logout();

    /**
     * 用户注册
     * @param userName
     * @param password
     * @return
     */
    Observable<StatusJson> register(String userName, String password);

}










