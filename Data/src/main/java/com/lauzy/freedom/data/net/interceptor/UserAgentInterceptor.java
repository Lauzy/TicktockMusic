package com.lauzy.freedom.data.net.interceptor;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Desc : 设置为 Chrome 浏览器的代理
 * Author : Lauzy
 * Date : 2017/8/1
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class UserAgentInterceptor implements Interceptor {
    private static final String USER_AGENT = "User-Agent";

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request original = chain.request();
        Request request = original
                .newBuilder()
                .addHeader(USER_AGENT, getUserAgent())
                .build();
        return chain.proceed(request);
    }

    private String getUserAgent() {
        return "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, " +
                "like Gecko) Chrome/59.0.3071.115 Safari/537.36";
    }
}