package online.ahayujie.aha_vocabulary_app.data;

import androidx.annotation.NonNull;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.base.BaseModel;
import online.ahayujie.aha_vocabulary_app.data.bean.LoginJson;
import online.ahayujie.aha_vocabulary_app.data.bean.StatusJson;
import online.ahayujie.aha_vocabulary_app.data.bean.WordJson;
import online.ahayujie.aha_vocabulary_app.data.bean.WordListJson;
import online.ahayujie.aha_vocabulary_app.data.bean.WordOnlineJson;
import online.ahayujie.aha_vocabulary_app.data.user_data_source.UserDataSource;
import online.ahayujie.aha_vocabulary_app.data.word_data_source.WordDataSource;
import retrofit2.Response;

/**
 * 数据仓库
 *
 * @author aha
 */
public class DataRepository extends BaseModel implements UserDataSource, WordDataSource {

    private static DataRepository INSTANCE = null;

    private final UserDataSource userDataSource;
    private final WordDataSource wordDataSource;

    private DataRepository(UserDataSource userDataSource, WordDataSource wordDataSource) {
        this.userDataSource = userDataSource;
        this.wordDataSource = wordDataSource;
    }

    public static DataRepository getInstance(@NonNull UserDataSource userDataSource,
                                             @NonNull WordDataSource wordDataSource) {
        if (INSTANCE ==  null) {
            INSTANCE = new DataRepository(userDataSource, wordDataSource);
        }
        return INSTANCE;
    }

    public static void destoryInstance() {
        INSTANCE = null;
    }

    /**
     * 获取token
     *
     * @return
     */
    @Override
    public String getToken() {
        return userDataSource.getToken();
    }

    /**
     * 保存token
     *
     * @param token
     */
    @Override
    public void saveToken(String token) throws IllegalArgumentException {
        userDataSource.saveToken(token);
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
        return userDataSource.login(userName, password);
    }

    /**
     * 用户退出登录
     */
    @Override
    public Observable logout() {
        return userDataSource.logout();
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
        return userDataSource.register(userName, password);
    }

    /**
     * 获取第page页的单词列表
     *
     * @param page
     * @return
     */
    @Override
    public Observable<Response<WordListJson>> getWordList(int page) {
        return wordDataSource.getWordList(page);
    }

    /**
     * 获取第page页的回收站的单词列表
     *
     * @param page
     * @return
     */
    @Override
    public Observable<Response<WordListJson>> getCleanList(int page) {
        return wordDataSource.getCleanList(page);
    }

    /**
     * 修改单词查询次数
     *
     * @param wordId
     * @param searchTimes
     * @return
     */
    @Override
    public Observable<Response<StatusJson>> modifyWordSearchTimes(int wordId, int searchTimes) {
        return wordDataSource.modifyWordSearchTimes(wordId, searchTimes);
    }

    /**
     * 修改单词是否放入回收站
     *
     * @param wordId
     * @param wordClean
     * @return
     */
    @Override
    public Observable<Response<StatusJson>> modifyWordClean(int wordId, int wordClean) {
        return wordDataSource.modifyWordClean(wordId, wordClean);
    }

    /**
     * 删除单词
     *
     * @param wordId
     * @return
     */
    @Override
    public Observable<Response<StatusJson>> deleteWord(int wordId) {
        return wordDataSource.deleteWord(wordId);
    }

    /**
     * 网络搜索单词
     *
     * @param wordSpell
     * @return
     */
    @Override
    public Observable<Response<WordOnlineJson>> searchWord(String wordSpell) {
        return wordDataSource.searchWord(wordSpell);
    }

    /**
     * 保存新单词
     *
     * @param wordSpell
     * @param wordTranslation
     * @return
     */
    @Override
    public Observable<Response<WordJson>> saveWord(String wordSpell, String wordTranslation) {
        return wordDataSource.saveWord(wordSpell, wordTranslation);
    }
}
