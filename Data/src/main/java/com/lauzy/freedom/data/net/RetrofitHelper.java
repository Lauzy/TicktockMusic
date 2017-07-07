package com.lauzy.freedom.data.net;

import android.content.Context;

import com.lauzy.freedom.data.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Desc : RetrofitHelper
 * Author : Lauzy
 * Date : 2017/7/4
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class RetrofitHelper {

    public RetrofitHelper() {
    }

    private OkHttpClient initOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            builder.addInterceptor(loggingInterceptor);
        }

       /* File cacheFile = new File(CacheUtils.getCacheDir(mContext), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        CacheInterceptor cacheInterceptor = new CacheInterceptor(mContext);
        builder.cache(cache);
        builder.interceptors().add(cacheInterceptor);//添加本地缓存拦截器，用来拦截本地缓存
        builder.networkInterceptors().add(cacheInterceptor);//添加网络拦截器，用来拦截网络数据*/

        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);

        return builder.build();
    }

    static class CacheUtils {

        static String getCacheDir(Context context) {
            String cacheDir;
            if (context.getExternalCacheDir() != null && existSDCard()) {
                cacheDir = context.getExternalCacheDir().toString();
            } else {
                cacheDir = context.getCacheDir().toString();
            }
            return cacheDir;
        }

        static boolean existSDCard() {
            return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        }
    }
}
