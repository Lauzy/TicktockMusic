package com.lauzy.freedom.data.local.data.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.lauzy.freedom.data.local.data.DataRepo;

import java.util.Map;

/**
 * Desc : SharedPref 仓库
 * Author : Lauzy
 * Date : 2017/12/14
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
@SuppressLint("ApplySharedPref")
public class SharedPreferenceDataRepo implements DataRepo {

    private final SharedPreferences mSharedPreferences;

    public SharedPreferenceDataRepo(Context context, String fileName, int mode) {
        mSharedPreferences = context.getSharedPreferences(fileName, mode);
    }

    @Override
    public void put(String key, String value) {
        mSharedPreferences.edit().putString(key, value).commit();
    }

    @Override
    public void put(String key, int value) {
        mSharedPreferences.edit().putInt(key, value).commit();
    }

    @Override
    public void put(String key, boolean value) {
        mSharedPreferences.edit().putBoolean(key, value).commit();
    }

    @Override
    public void put(String key, long value) {
        mSharedPreferences.edit().putLong(key, value).commit();
    }

    @Override
    public String getString(String key) {
        return getString(key, "");
    }

    @Override
    public String getString(String key, String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }

    @Override
    public int getInt(String key) {
        return getInt(key, -1);
    }

    @Override
    public int getInt(String key, int defaultValue) {
        return mSharedPreferences.getInt(key, defaultValue);
    }

    @Override
    public long getLong(String key) {
        return mSharedPreferences.getLong(key, -1);
    }

    @Override
    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return mSharedPreferences.getBoolean(key, defaultValue);
    }

    @Override
    public void remove(String key) {
        mSharedPreferences.edit().remove(key).commit();
    }

    @Override
    public Map<String, ?> getAll() {
        return mSharedPreferences.getAll();
    }

    @Override
    public boolean contains(String key) {
        return mSharedPreferences.contains(key);
    }

    @Override
    public void clear() {
        mSharedPreferences.edit().clear().commit();
    }

}
