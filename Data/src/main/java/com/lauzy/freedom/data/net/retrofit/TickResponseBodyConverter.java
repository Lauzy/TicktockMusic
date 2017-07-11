package com.lauzy.freedom.data.net.retrofit;

import android.support.annotation.NonNull;

import com.freedom.lauzy.model.StatusBean;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.lauzy.freedom.data.exception.ErrorMsgException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import static okhttp3.internal.Util.UTF_8;

/**
 * Desc : 自定义响应转换器
 * Author : Lauzy
 * Date : 2017/7/11
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class TickResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson mGson;
    private final TypeAdapter<T> mTypeAdapter;

    TickResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        mGson = gson;
        mTypeAdapter = adapter;
    }

    @Override
    public T convert(@NonNull ResponseBody value) throws IOException {
        String response = value.string();
        try {
            StatusBean status = mGson.fromJson(response, StatusBean.class);
            if (status.isInvalidCode()) {//这里可具体判断多种状态
                value.close();
                throw new ErrorMsgException(status.error_code, status.error_msg);
            }

            MediaType contentType = value.contentType();
            Charset charset = contentType != null ? contentType.charset(UTF_8) : UTF_8;
            InputStream inputStream = new ByteArrayInputStream(response.getBytes());
            if (charset == null)
                throw new NullPointerException("charset == null");
            Reader reader = new InputStreamReader(inputStream, charset);
            JsonReader jsonReader = mGson.newJsonReader(reader);
            return mTypeAdapter.read(jsonReader);
        } finally {
            value.close();
        }
    }
}
