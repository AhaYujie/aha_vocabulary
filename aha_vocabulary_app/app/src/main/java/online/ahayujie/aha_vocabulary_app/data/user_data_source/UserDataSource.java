package online.ahayujie.aha_vocabulary_app.data.user_data_source;

import io.reactivex.Observable;
import online.ahayujie.aha_vocabulary_app.data.bean.LoginJson;
import online.ahayujie.aha_vocabulary_app.data.bean.StatusJson;
import retrofit2.Response;

/**
 * 用户数据源接口
 *
 * @author aha
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
    void saveToken(String token);

    /**
     * 删除token
     */
    void deleteToken();

    /**
     * 保存用户名
     * @param userName
     */
    void saveUserName(String userName);

    /**
     * 获取用户名
     * @return
     */
    String getUserName();

    /**
     * 删除用户名
     */
    void deleteUserName();

    /**
     * 用户登录
     * @param userName
     * @param password
     * @return
     */
    Observable<LoginJson> login(String userName, String password);

    /**
     * 用户退出登录
     *
     * @return
     */
    Observable<Response<Void>> logout();

    /**
     * 用户注册
     * @param userName
     * @param password
     * @return
     */
    Observable<StatusJson> register(String userName, String password);

}










