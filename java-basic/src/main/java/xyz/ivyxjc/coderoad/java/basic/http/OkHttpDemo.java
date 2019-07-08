package xyz.ivyxjc.coderoad.java.basic.http;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class OkHttpDemo {

    private static final Logger log = LoggerFactory.getLogger(OkHttpDemo.class);

    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://www.baidu.com")
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                log.info("response body is:\n {}", response.body().string());
            } else {
                log.error("response body is null");
            }
        } catch (IOException e) {
            log.error("call fail", e);
        }


    }
}
