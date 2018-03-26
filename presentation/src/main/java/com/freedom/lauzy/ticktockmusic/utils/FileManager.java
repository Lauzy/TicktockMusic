package com.freedom.lauzy.ticktockmusic.utils;

import android.os.Environment;

import com.freedom.lauzy.ticktockmusic.TicktockApplication;

import java.io.File;

/**
 * Desc : 文件管理
 * Author : Lauzy
 * Date : 2018/3/26
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class FileManager {

    //临时文件目录名称
    private static final String TEMP_DIR_NAME = "temp";
    //缓存文件目录名称
    private static final String CACHE_DIR_NAME = "cache";
    //歌词文件目录
    private static final String LRC_DIR_NAME = "lrc";

    private static final FileManager INSTANCE = new FileManager();

    public static FileManager getInstance() {
        return INSTANCE;
    }

    /**
     * 获取app文件目录根路径
     */
    public File getRootDir(boolean useSDCard) {

        if (useSDCard && isSDCardAvailable()) {
            String packageName = TicktockApplication.getInstance().getPackageName();
            File file = new File(Environment.getExternalStorageDirectory(), packageName);
            if (file.exists() || file.mkdir()) {
                return file;
            }
        }
        return TicktockApplication.getInstance().getFilesDir();
    }

    public boolean isSDCardAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                && Environment.getExternalStorageDirectory().canWrite();
    }

    public File getRootDir() {
        return getRootDir(true);
    }

    public File getTempDir(boolean useSDCard) {

        File file = new File(getRootDir(useSDCard), TEMP_DIR_NAME);
        if (!file.exists()) {
            boolean success = file.mkdir();
            if (!success) {
                return null;
            }
        }
        return file;
    }

    public File getTempDir() {
        return getTempDir(true);
    }

    public File getCacheDir() {
        return getCacheDir(true);
    }

    public File getCacheDir(boolean useSDCard) {

        File file = new File(getRootDir(useSDCard), CACHE_DIR_NAME);
        if (!file.exists()) {
            boolean success = file.mkdir();
            if (!success) {
                return null;
            }
        }
        return file;
    }

    public File getLrcDir() {
        File file = new File(getRootDir(true), LRC_DIR_NAME);
        if (!file.exists()) {
            boolean success = file.mkdir();
            if (!success) {
                return null;
            }
        }
        return file;
    }

}
