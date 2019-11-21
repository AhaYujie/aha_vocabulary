package online.ahayujie.util;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class HttpUtil {

    private static OkHttpClient httpClient = new OkHttpClient();

    private HttpUtil() {}

    public static Response httpPost(String url, FormBody formBody) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        return httpClient.newCall(request).execute();
    }

}
