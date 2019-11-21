package online.ahayujie.service.impl;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import okhttp3.FormBody;
import okhttp3.Response;
import online.ahayujie.service.TranslationService;
import online.ahayujie.util.HttpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class YouDaoTranslationServiceImpl implements TranslationService {

    @Value("${YOUDAO_API_URL}")
    private String URL;
    @Value("${YOUDAO_API_APP_KEY}")
    private String APP_KEY;
    @Value("${YOUDAO_API_APP_SECRET}")
    private String APP_SECRET;
    @Value("${YOUDAO_API_SIGN_TYPE}")
    private String SIGN_TYPE;

    @Override
    public String translateWord(String word) throws IOException, NullPointerException {
        String salt = String.valueOf(System.currentTimeMillis());
        String currentTime = String.valueOf(System.currentTimeMillis() / 1000);
        String signStr = APP_KEY + truncate(word) + salt + currentTime + APP_SECRET;
        String sign = getDigest(signStr);
        FormBody formBody = new FormBody.Builder()
                .add("q", word)    // 翻译文本
                .add("from", "en") // 翻译文本语种
                .add("to", "zh-CHS")    // 翻译后的文本语种
                .add("appKey", APP_KEY)  // app id
                .add("signType", SIGN_TYPE)
                .add("curtime", currentTime)
                .add("salt", salt)
                .add("sign", sign)
                .build();
        Response response = HttpUtil.httpPost(URL, formBody);
        if (response.body() == null) {
            throw new NullPointerException("http response body is null");
        }
        TranslationBean translationBean = new Gson().fromJson(response.body().string(), TranslationBean.class);
        if (translationBean == null || translationBean.getTranslation() == null ||
                translationBean.getTranslation().isEmpty()) {
            throw new NullPointerException("http response body translation is null");
        }
        StringBuilder translations = new StringBuilder();
        // 如果 web translation 存在
        if (!translationBean.getWeb().isEmpty()) {
            List<String> webTranslations = translationBean.getWeb().get(0).getValue();
            for (int index = 0; index < webTranslations.size() - 1; index++) {
                translations.append(webTranslations.get(index)).append(", ");
            }
            translations.append(webTranslations.get(webTranslations.size() - 1));
            return translations.toString();
        }
        for (int index = 0; index < translationBean.getTranslation().size() - 1; index++) {
            translations.append(translationBean.getTranslation().get(index)).append(", ");
        }
        translations.append(translationBean.getTranslation().get(translationBean.getTranslation().size() - 1));
        return translations.toString();
    }

    /**
     * 生成加密字段
     */
    private String getDigest(String string) {
        if (string == null) {
            return null;
        }
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        byte[] btInput = string.getBytes(StandardCharsets.UTF_8);
        try {
            MessageDigest mdInst = MessageDigest.getInstance("SHA-256");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    private String truncate(String q) {
        if (q == null) {
            return null;
        }
        int len = q.length();
        String result;
        return len <= 20 ? q : (q.substring(0, 10) + len + q.substring(len - 10, len));
    }

    static class TranslationBean {

        /**
         * tSpeakUrl : http://openapi.youdao.com/ttsapi?q=%E8%B4%AA%E5%A9%AA%E7%9A%84&langType=zh-CHS&sign=347E6CF6937CAB8365A0FD029A6BC38F&salt=1574134211016&voice=4&format=mp3&appKey=3f1dac3473556fa5
         * returnPhrase : ["greedy"]
         * web : [{"value":["贪婪","贪心","贪吃的","渴望的"],"key":"Greedy"},{"value":["贪婪的继承人","贪心的继续人"],"key":"Greedy Heirs"},{"value":["贪心鬼"],"key":"greedy ghouls"}]
         * query : greedy
         * translation : ["贪婪的"]
         * errorCode : 0
         * dict : {"url":"yddict://m.youdao.com/dict?le=eng&q=greedy"}
         * webdict : {"url":"http://m.youdao.com/dict?le=eng&q=greedy"}
         * basic : {"exam_type":["高中","SAT","CET4","考研"],"us-phonetic":"ˈɡriːdi","phonetic":"ˈɡriːdi","uk-phonetic":"ˈɡriːdi","wfs":[{"wf":{"name":"比较级","value":"greedier"}},{"wf":{"name":"最高级","value":"greediest"}}],"uk-speech":"http://openapi.youdao.com/ttsapi?q=greedy&langType=en&sign=60F334494D10DFC45C276B180FBAFCFD&salt=1574134211016&voice=5&format=mp3&appKey=3f1dac3473556fa5","explains":["adj. 贪婪的；贪吃的；渴望的"],"us-speech":"http://openapi.youdao.com/ttsapi?q=greedy&langType=en&sign=60F334494D10DFC45C276B180FBAFCFD&salt=1574134211016&voice=6&format=mp3&appKey=3f1dac3473556fa5"}
         * l : en2zh-CHS
         * speakUrl : http://openapi.youdao.com/ttsapi?q=greedy&langType=en&sign=60F334494D10DFC45C276B180FBAFCFD&salt=1574134211016&voice=4&format=mp3&appKey=3f1dac3473556fa5
         */

        private String tSpeakUrl;
        private String query;
        private String errorCode;
        private DictBean dict;
        private WebdictBean webdict;
        private BasicBean basic;
        private String l;
        private String speakUrl;
        private List<String> returnPhrase;
        private List<WebBean> web;
        private List<String> translation;

        public String getTSpeakUrl() {
            return tSpeakUrl;
        }

        public void setTSpeakUrl(String tSpeakUrl) {
            this.tSpeakUrl = tSpeakUrl;
        }

        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public DictBean getDict() {
            return dict;
        }

        public void setDict(DictBean dict) {
            this.dict = dict;
        }

        public WebdictBean getWebdict() {
            return webdict;
        }

        public void setWebdict(WebdictBean webdict) {
            this.webdict = webdict;
        }

        public BasicBean getBasic() {
            return basic;
        }

        public void setBasic(BasicBean basic) {
            this.basic = basic;
        }

        public String getL() {
            return l;
        }

        public void setL(String l) {
            this.l = l;
        }

        public String getSpeakUrl() {
            return speakUrl;
        }

        public void setSpeakUrl(String speakUrl) {
            this.speakUrl = speakUrl;
        }

        public List<String> getReturnPhrase() {
            return returnPhrase;
        }

        public void setReturnPhrase(List<String> returnPhrase) {
            this.returnPhrase = returnPhrase;
        }

        public List<WebBean> getWeb() {
            return web;
        }

        public void setWeb(List<WebBean> web) {
            this.web = web;
        }

        public List<String> getTranslation() {
            return translation;
        }

        public void setTranslation(List<String> translation) {
            this.translation = translation;
        }

        public static class DictBean {
            /**
             * url : yddict://m.youdao.com/dict?le=eng&q=greedy
             */

            private String url;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class WebdictBean {
            /**
             * url : http://m.youdao.com/dict?le=eng&q=greedy
             */

            private String url;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class BasicBean {
            /**
             * exam_type : ["高中","SAT","CET4","考研"]
             * us-phonetic : ˈɡriːdi
             * phonetic : ˈɡriːdi
             * uk-phonetic : ˈɡriːdi
             * wfs : [{"wf":{"name":"比较级","value":"greedier"}},{"wf":{"name":"最高级","value":"greediest"}}]
             * uk-speech : http://openapi.youdao.com/ttsapi?q=greedy&langType=en&sign=60F334494D10DFC45C276B180FBAFCFD&salt=1574134211016&voice=5&format=mp3&appKey=3f1dac3473556fa5
             * explains : ["adj. 贪婪的；贪吃的；渴望的"]
             * us-speech : http://openapi.youdao.com/ttsapi?q=greedy&langType=en&sign=60F334494D10DFC45C276B180FBAFCFD&salt=1574134211016&voice=6&format=mp3&appKey=3f1dac3473556fa5
             */

            @SerializedName("us-phonetic")
            private String usphonetic;
            private String phonetic;
            @SerializedName("uk-phonetic")
            private String ukphonetic;
            @SerializedName("uk-speech")
            private String ukspeech;
            @SerializedName("us-speech")
            private String usspeech;
            private List<String> exam_type;
            private List<WfsBean> wfs;
            private List<String> explains;

            public String getUsphonetic() {
                return usphonetic;
            }

            public void setUsphonetic(String usphonetic) {
                this.usphonetic = usphonetic;
            }

            public String getPhonetic() {
                return phonetic;
            }

            public void setPhonetic(String phonetic) {
                this.phonetic = phonetic;
            }

            public String getUkphonetic() {
                return ukphonetic;
            }

            public void setUkphonetic(String ukphonetic) {
                this.ukphonetic = ukphonetic;
            }

            public String getUkspeech() {
                return ukspeech;
            }

            public void setUkspeech(String ukspeech) {
                this.ukspeech = ukspeech;
            }

            public String getUsspeech() {
                return usspeech;
            }

            public void setUsspeech(String usspeech) {
                this.usspeech = usspeech;
            }

            public List<String> getExam_type() {
                return exam_type;
            }

            public void setExam_type(List<String> exam_type) {
                this.exam_type = exam_type;
            }

            public List<WfsBean> getWfs() {
                return wfs;
            }

            public void setWfs(List<WfsBean> wfs) {
                this.wfs = wfs;
            }

            public List<String> getExplains() {
                return explains;
            }

            public void setExplains(List<String> explains) {
                this.explains = explains;
            }

            public static class WfsBean {
                /**
                 * wf : {"name":"比较级","value":"greedier"}
                 */

                private WfBean wf;

                public WfBean getWf() {
                    return wf;
                }

                public void setWf(WfBean wf) {
                    this.wf = wf;
                }

                public static class WfBean {
                    /**
                     * name : 比较级
                     * value : greedier
                     */

                    private String name;
                    private String value;

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getValue() {
                        return value;
                    }

                    public void setValue(String value) {
                        this.value = value;
                    }
                }
            }
        }

        public static class WebBean {
            /**
             * value : ["贪婪","贪心","贪吃的","渴望的"]
             * key : Greedy
             */

            private String key;
            private List<String> value;

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public List<String> getValue() {
                return value;
            }

            public void setValue(List<String> value) {
                this.value = value;
            }
        }
    }

}
