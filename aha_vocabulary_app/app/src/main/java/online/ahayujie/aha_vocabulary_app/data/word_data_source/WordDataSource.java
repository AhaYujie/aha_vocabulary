package online.ahayujie.aha_vocabulary_app.data.word_data_source;

import io.reactivex.Observable;
import online.ahayujie.aha_vocabulary_app.data.bean.StatusJson;
import online.ahayujie.aha_vocabulary_app.data.bean.WordJson;
import online.ahayujie.aha_vocabulary_app.data.bean.WordListJson;
import online.ahayujie.aha_vocabulary_app.data.bean.WordOnlineJson;
import retrofit2.Response;

/**
 * 单词数据源接口
 *
 * @author aha
 */
public interface WordDataSource {

    /**
     * 获取第page页的单词列表
     * @param page
     * @return
     */
    Observable<Response<WordListJson>> getWordList(int page);

    /**
     * 获取第page页的回收站的单词列表
     * @param page
     * @return
     */
    Observable<Response<WordListJson>> getCleanList(int page);

    /**
     * 修改单词查询次数
     * @param wordId
     * @param searchTimes
     * @return
     */
    Observable<Response<StatusJson>> modifyWordSearchTimes(int wordId, int searchTimes);

    /**
     * 修改单词是否放入回收站
     * @param wordId
     * @param wordClean 单词是否放入回收站(0为放入，1为不放入)
     * @return
     */
    Observable<Response<StatusJson>> modifyWordClean(int wordId, int wordClean);

    /**
     * 删除单词
     * @param wordId
     * @return
     */
    Observable<Response<StatusJson>> deleteWord(int wordId);

    /**
     * 网络搜索单词
     * @param wordSpell
     * @return
     */
    Observable<Response<WordOnlineJson>> searchWord(String wordSpell);

    /**
     * 保存新单词
     * @param wordSpell
     * @param wordTranslation
     * @return
     */
    Observable<Response<WordJson>> saveWord(String wordSpell, String wordTranslation);

}
