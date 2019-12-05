package online.ahayujie.aha_vocabulary_app.data.word_data_source.impl;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import online.ahayujie.aha_vocabulary_app.data.bean.StatusJson;
import online.ahayujie.aha_vocabulary_app.data.bean.Word;
import online.ahayujie.aha_vocabulary_app.data.bean.WordJson;
import online.ahayujie.aha_vocabulary_app.data.bean.WordListJson;
import online.ahayujie.aha_vocabulary_app.data.bean.WordOnlineJson;
import online.ahayujie.aha_vocabulary_app.data.user_data_source.UserDataSource;
import online.ahayujie.aha_vocabulary_app.data.word_data_source.WordDataSource;
import online.ahayujie.aha_vocabulary_app.data.word_data_source.network.api.WordService;
import retrofit2.Response;

/**
 * 单词数据源实现类
 * @author aha
 */
public class WordDataSourceImpl implements WordDataSource {

    private static final int PAGE_SIZE = 20;

    private WordService wordService;
    private UserDataSource userDataSource;

    private static WordDataSourceImpl INSTANCE = null;

    public static WordDataSource getInstance(WordService wordService, UserDataSource userDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new WordDataSourceImpl(wordService, userDataSource);
        }
        return INSTANCE;
    }

    public static void destoryInstance() {
        INSTANCE = null;
    }

    private WordDataSourceImpl(WordService wordService, UserDataSource userDataSource) {
        this.wordService = wordService;
        this.userDataSource = userDataSource;
    }

    /**
     * 获取第page页的单词列表
     *
     * @param page
     * @return
     */
    @Override
    public Observable<Response<WordListJson>> getWordList(int page) {
        return wordService.getWordList(1, page, PAGE_SIZE, userDataSource.getToken());
    }

    /**
     * 获取第page页的回收站的单词列表
     *
     * @param page
     * @return
     */
    @Override
    public Observable<Response<WordListJson>> getCleanList(int page) {
        return wordService.getWordList(0, page, PAGE_SIZE, userDataSource.getToken());
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
        Map<String, Object> map = new HashMap<>(2);
        map.put("word_id", wordId);
        map.put("word_search_times", searchTimes);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                new Gson().toJson(map));
        return wordService.modifyWordSearchTimes(userDataSource.getToken(), requestBody);
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
        Map<String, Object> map = new HashMap<>(2);
        map.put("word_id", wordId);
        map.put("word_clean", wordClean);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                new Gson().toJson(map));
        return wordService.modifyWordClean(userDataSource.getToken(), requestBody);
    }

    /**
     * 删除单词
     *
     * @param wordId
     * @return
     */
    @Override
    public Observable<Response<StatusJson>> deleteWord(int wordId) {
        return wordService.deleteWord(userDataSource.getToken(), wordId);
    }

    /**
     * 网络搜索单词
     *
     * @param wordSpell
     * @return
     */
    @Override
    public Observable<Response<WordOnlineJson>> searchWord(String wordSpell) {
        return wordService.searchWord(userDataSource.getToken(), wordSpell);
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
        return wordService.saveWord(userDataSource.getToken(), wordSpell, wordTranslation);
    }
}
