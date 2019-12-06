package online.ahayujie.aha_vocabulary_app.data.user_data_source.network.api;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import online.ahayujie.aha_vocabulary_app.data.bean.LoginJson;
import online.ahayujie.aha_vocabulary_app.data.bean.StatusJson;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 用户接口
 * @author aha
 */
public interface UserService {

    /**
     * 用户登录
     * @param user 包含用户名和密码
     * @return
     */
    @POST("token")
    @Headers({"Content-Type: application/json","Accept: application/json"})
    Observable<LoginJson> login(@Body RequestBody user);

    /**
     * 用户退出登录
     * @param token
     * @return
     */
    @DELETE("token/logout")
    Observable<Response<Void>> logout(@Header("token") String token);


    /**
     * 用户注册
     * @param user 包含用户名和密码
     * @return
     */
    @POST("user")
    @Headers({"Content-Type: application/json","Accept: application/json"})
    Observable<StatusJson> register(@Body RequestBody user);

}












