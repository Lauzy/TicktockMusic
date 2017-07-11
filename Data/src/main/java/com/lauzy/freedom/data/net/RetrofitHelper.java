package com.lauzy.freedom.data.net;

import android.content.Context;

import com.lauzy.freedom.data.BuildConfig;
import com.lauzy.freedom.data.net.constants.NetConstants;
import com.lauzy.freedom.data.net.interceptor.CacheInterceptor;
import com.lauzy.freedom.data.net.retrofit.TickJsonConverterFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Desc : RetrofitHelper
 * Author : Lauzy
 * Date : 2017/7/4
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class RetrofitHelper {
    private Retrofit mRetrofit;

    public RetrofitHelper(Context context) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(NetConstants.BASE_API)
                .client(initOkHttp(context))
                .addConverterFactory(TickJsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

   /* public void setContext(Context context) {
        mContext = context;
    }*/

    public <T> T createApi(Class<T> paramClass) {
        return mRetrofit.create(paramClass);
    }

    private OkHttpClient initOkHttp(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            builder.addInterceptor(loggingInterceptor);
        }

        if (null != context) {
            File cacheFile = new File(CacheUtils.getCacheDir(context));
            Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
            CacheInterceptor cacheInterceptor = new CacheInterceptor(context);
            builder.cache(cache);
            builder.addInterceptor(cacheInterceptor);
//            builder.networkInterceptors().add(cacheInterceptor);//添加网络拦截器，用来拦截网络数据
        }

        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);

        return builder.build();
    }

    private static class CacheUtils {

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
