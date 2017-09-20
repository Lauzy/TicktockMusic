package com.freedom.lauzy.ticktockmusic.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Base64;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Desc : 文件操作
 * Author : Lauzy
 * Date : 2017/9/20
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class FileUtil {

    private static final String THUMBNAIL = "ttThumbnail";

    public static String getDirPath(Context context, String dirName) {
        String dirPath = "";
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File filesDir = context.getExternalFilesDir(null);
            if (filesDir != null) {
                dirPath = filesDir.getAbsolutePath();
            }
        } else {
            dirPath = context.getFilesDir().getAbsolutePath();
        }
        return dirPath + File.separator + dirName;//文件夹
    }


    public static String getCachePath(Context context) {
        String path = null;
        File cacheDir = context.getExternalCacheDir();
        if (cacheDir != null) {
            path = cacheDir.getAbsolutePath();
        }
        return path;
    }

    public static String getThumbnailName(String... name) {
        StringBuilder builder = new StringBuilder();
        for (String s : name) {
            builder.append(s);
        }
        return new String(Base64.encode(builder.toString().getBytes(), Base64.DEFAULT)) + ".jpg";
    }

    public static String getThumbnailPath(Context context, String... name) {
        String dirPath = getDirPath(context, THUMBNAIL);
        return dirPath + File.separator + getThumbnailName(name);
    }

    /**
     * 保存缩略图到本地
     *
     * @param bitmap bitmap
     * @param name   参数
     */
    public static void saveThumbnail(Context context, Bitmap bitmap, String... name) {
        BufferedOutputStream bos = null;
        try {
            String dirPath = getDirPath(context, THUMBNAIL);
            File dir = new File(dirPath);
            boolean exists = dir.exists();
            if (!exists) {
                exists = dir.mkdir();
            }
            if (exists) {
                File imageFile = new File(dirPath + File.separator + getThumbnailName(name));
                bos = new BufferedOutputStream(new FileOutputStream(imageFile));
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                bos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
