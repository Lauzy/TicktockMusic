package com.lauzy.freedom.data.local.data;

import java.util.Map;

/**
 * Desc : 数据仓库接口
 * Author : Lauzy
 * Date : 2017/12/14
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface DataRepo {

    void put(String key, String value);

    void put(String key, int value);

    void put(String key, boolean value);

    void put(String key, long value);

    String getString(String key);

    String getString(String key, String defaultValue);

    int getInt(String key);

    int getInt(String key, int defaultValue);

    long getLong(String key);

    boolean getBoolean(String key);

    boolean getBoolean(String key, boolean defaultValue);

    void remove(String key);

    Map<String, ?> getAll();

    boolean contains(String key);

    void clear();
}
