package online.ahayujie.aha_vocabulary_app.data.word_data_source.network.api;

import io.reactivex.Observable;
import online.ahayujie.aha_vocabulary_app.data.bean.StatusJson;
import online.ahayujie.aha_vocabulary_app.data.bean.WordJson;
import online.ahayujie.aha_vocabulary_app.data.bean.WordListJson;
import online.ahayujie.aha_vocabulary_app.data.bean.WordOnlineJson;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * 单词接口
 *
 * @author aha
 */
public interface WordService {

    /**
     * 获取单词列表接口
     * @param wordClean 是否是被放入回收站的单词(0为放入的，1为不放入的)
     * @param page 页数索引
     * @param pageSize 单页大小
     * @param token 用户token
     * @return
     */
    @FormUrlEncoded
    @GET("word")
    Observable<Response<WordListJson>> getWordList(@Field("word_clean") int wordClean, @Field("page") int page,
                                                  @Field("page_size") int pageSize,
                                                  @Header("token") String token);

    /**
     * 修改单词查询次数接口
     * @param token 用户token
     * @param wordId 单词id
     * @param wordSearchTimes 单词查询次数
     * @return
     */
    @FormUrlEncoded
    @PUT("word")
    Observable<Response<StatusJson>> modifyWordSearchTimes(@Header("token") String token,
                                                 @Field("word_id") int wordId,
                                                 @Field("word_search_times") int wordSearchTimes);

    /**
     * 修改单词是否放入回收站
     * @param token 用户token
     * @param wordId 单词id
     * @param wordClean 单词是否放入回收站(0为放入，1为不放入)
     * @return
     */
    @FormUrlEncoded
    @PUT("word")
    Observable<Response<StatusJson>> modifyWordClean(@Header("token") String token,
                                           @Field("word_id") int wordId,
                                           @Field("word_clean") int wordClean);

    /**
     * 删除单词
     * @param token 用户token
     * @param wordId 单词id
     * @return
     */
    @FormUrlEncoded
    @DELETE("word")
    Observable<Response<StatusJson>> deleteWord(@Header("token") String token, @Field("word_id") int wordId);

    /**
     * 网络搜索单词
     * @param token 用户token
     * @param wordSpell 单词拼写
     * @return
     */
    @FormUrlEncoded
    @GET("word/online")
    Observable<Response<WordOnlineJson>> searchWord(@Header("token") String token,
                                          @Field("word_spell") String wordSpell);

    /**
     * 保存新单词
     * @param token 用户token
     * @param wordSpell 单词拼写
     * @param wordTranslation 单词翻译
     * @return
     */
    @FormUrlEncoded
    @POST("word")
    Observable<Response<WordJson>> saveWord(@Header("token") String token,
                                  @Field("word_spell") String wordSpell,
                                  @Field("word_translation") String wordTranslation);

}
