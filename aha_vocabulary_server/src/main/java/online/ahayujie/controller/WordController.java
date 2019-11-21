package online.ahayujie.controller;

import com.alibaba.fastjson.JSONObject;
import online.ahayujie.pojo.Word;
import online.ahayujie.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/word")
public class WordController {

    private WordService wordService;

    @Autowired
    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    /**
     * 获取单词列表
     * @param wordClean
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping
    public List<Word> getWordList(@RequestParam(value = "word_clean", required = false, defaultValue = "1") Long wordClean,
                                  @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
                                  @RequestParam(value = "page_size", required = false, defaultValue = "20") Long pageSize,
                                  HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("user_id");
        return wordService.getWordList(userId, wordClean, page, pageSize);
    }

    /**
     * 更新单词的数据
     * 可以更新一个单词的一个或多个属性，除了 wordId 和 wordUserId
     * @param word
     * @return
     */
    @PutMapping(produces = "application/json;charset=utf-8")
    public String updateWord(@RequestBody Word word) {
        JSONObject jsonObject = new JSONObject();
        try {
            wordService.updateWord(word);
            jsonObject.put("status", 1);
        }
        catch (NoSuchElementException e) {
            e.printStackTrace();
            jsonObject.put("status", 0);
        }
        return jsonObject.toJSONString();
    }

    /**
     * 删除单词
     * @param wordId
     * @return
     */
    @DeleteMapping(produces = "application/json;charset=utf-8")
    public String deleteWord(@RequestParam("word_id") Long wordId) {
        JSONObject jsonObject = new JSONObject();
        try {
            wordService.deleteWord(wordId);
            jsonObject.put("status", 1);
        }
        catch (NoSuchElementException e) {
            e.printStackTrace();
            jsonObject.put("status", 0);
        }
        return jsonObject.toJSONString();
    }

    /**
     * 网络搜索单词
     * @param wordSpell
     * @return
     */
    @GetMapping(value = "/online", produces = "application/json;charset=utf-8")
    public String searchWord(@RequestParam("word_spell") String wordSpell) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("word_spell", wordSpell);
        try {
            Word word = wordService.searchWord(wordSpell);
            jsonObject.put("status", 1);
            jsonObject.put("word_translation", word.getWordTranslation());
        }
        catch (IOException | NullPointerException e) {
            e.printStackTrace();
            jsonObject.put("status", 0);
        }
        return jsonObject.toJSONString();
    }

    /**
     * 保存新的单词
     * @param wordSpell
     * @param wordTranslation
     * @return
     */
    @PostMapping(produces = "application/json;charset=utf-8")
    public String saveWord(@RequestParam("word_spell") String wordSpell,
                           @RequestParam("word_translation") String wordTranslation,
                           HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("user_id");
        JSONObject jsonObject = new JSONObject();
        try {
            Word word = wordService.saveWord(wordSpell, wordTranslation, userId);
            jsonObject.put("status", 1);
            jsonObject.put("word", word);
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
            jsonObject.put("status", 0);
        }
        return jsonObject.toJSONString();
    }

}













