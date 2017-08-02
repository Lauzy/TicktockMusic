package com.lauzy.freedom.data.net;

import com.lauzy.freedom.data.BuildConfig;
import com.lauzy.freedom.data.net.constants.NetConstants;
import com.lauzy.freedom.data.net.interceptor.UserAgentInterceptor;
import com.lauzy.freedom.data.net.retrofit.TickJsonConverterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Desc : RetrofitHelper
 * Author : Lauzy
 * Date : 2017/7/4
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public enum RetrofitHelper {

    INSTANCE;

    private Retrofit mRetrofit;

    RetrofitHelper() {
        mRetrofit = new Retrofit.Builder()
                .client(initOkHttp())
                .baseUrl(NetConstants.BASE_API)
                .addConverterFactory(TickJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public <T> T createApi(Class<T> paramClass) {
        return mRetrofit.create(paramClass);
    }

    private OkHttpClient initOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }
        builder.addInterceptor(new UserAgentInterceptor());//reset User-Agent
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);
        return builder.build();
    }
}
