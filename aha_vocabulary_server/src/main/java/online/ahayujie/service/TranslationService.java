package online.ahayujie.service;

import java.io.IOException;

public interface TranslationService {

    /**
     * 翻译英文为中文
     * @param word
     * @return
     * @throws IOException
     * @throws NullPointerException
     */
    String translateWord(String word) throws IOException, NullPointerException;

}
