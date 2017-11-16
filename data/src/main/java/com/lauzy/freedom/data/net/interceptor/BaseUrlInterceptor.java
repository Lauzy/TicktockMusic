package com.lauzy.freedom.data.net.interceptor;

import android.support.annotation.NonNull;

import com.lauzy.freedom.data.net.constants.NetConstants;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Desc : 切换BaseUrl拦截器
 * Author : Lauzy
 * Date : 2017/9/28
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class BaseUrlInterceptor implements Interceptor {
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        String urlHead = originalRequest.headers().get(NetConstants.Header.BASE_URL_HEAD);
        HttpUrl oldHttpUrl = originalRequest.url();
//        Request.Builder builder = originalRequest.newBuilder();
        if (urlHead != null && !urlHead.isEmpty()) {
            HttpUrl newBaseUrl;
            Request.Builder builder;
//            builder.removeHeader(NetConstants.Header.BASE_URL_HEAD);
            if (NetConstants.Header.BAIDU_HEAD_CONTENT.equals(urlHead)) {
                newBaseUrl = HttpUrl.parse(NetConstants.BASE_API);
                builder = originalRequest
                        .newBuilder()
                        .addHeader(NetConstants.Header.USER_AGENT, NetConstants.Header.USER_AGENT_CONTENT);
            } else if (NetConstants.Header.BANDSINTOWN_HEAD_CONTENT.equals(urlHead)) {
                newBaseUrl = HttpUrl.parse(NetConstants.BASE_ARTIST_URL);
                builder = originalRequest.newBuilder();
            } else {
                newBaseUrl = oldHttpUrl;
                builder = originalRequest.newBuilder();
            }
            if (null == newBaseUrl) {
                return null;
            }
            HttpUrl newFullUrl = oldHttpUrl
                    .newBuilder()
                    .scheme(newBaseUrl.scheme())
                    .host(newBaseUrl.host())
                    .port(newBaseUrl.port())
                    .build();
            return chain.proceed(builder.url(newFullUrl).build());
        } else {
            return chain.proceed(originalRequest);
        }
    }
}
