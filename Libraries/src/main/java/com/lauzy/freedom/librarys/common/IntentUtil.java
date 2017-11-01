package com.lauzy.freedom.librarys.common;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import com.lauzy.freedom.librarys.R;

import java.io.File;

/**
 * Desc : IntentUtil
 * Author : Lauzy
 * Date : 2017/8/17
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class IntentUtil {

    /**
     * 打开系统设置界面
     *
     * @param context context
     * @return intent
     */
    public static Intent openSetting(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        return intent;
    }

    /**
     * 分享文件
     * @param context context
     * @param filePath 文件路径
     */
    public static void shareFile(Context context, String filePath) {
        File file = new File(filePath);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("audio/*");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        context.startActivity(Intent.createChooser(intent, context.getResources().getString(R.string.share)));
    }
}
