package com.freedom.lauzy.ticktockmusic.utils;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.freedom.lauzy.interactor.ConfigManagerUseCase;
import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.TicktockApplication;
import com.lauzy.freedom.data.repository.ConfigDataManagerImpl;
import com.lauzy.freedom.librarys.common.NetworkUtils;
import com.lauzy.freedom.librarys.common.ToastUtils;

/**
 * Desc : 检查网络
 * Author : Lauzy
 * Date : 2018/4/2
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class CheckNetwork {

    private static final ConfigManagerUseCase sConfigManagerUseCase = new ConfigManagerUseCase
            (new ConfigDataManagerImpl(TicktockApplication.getInstance()));


    public static void checkNetwork(Context context, OnPositiveListener listener) {
        if (!NetworkUtils.isConnect(context)) {
            ToastUtils.showSingle(context, context.getString(R.string.network_error));
            return;
        }
        if (NetworkUtils.isMobileNetwork(context) && !sConfigManagerUseCase.isEnablePlayByNetwork()) {
            new MaterialDialog.Builder(context)
                    .content(R.string.mobile_network_tip)
                    .positiveText(android.R.string.yes)
                    .negativeText(android.R.string.cancel)
                    .onPositive((dialog, which) -> {
                        if (listener != null) {
                            sConfigManagerUseCase.enablePlayByNetwork(true);
                            listener.onPositive();
                        }
                    })
                    .build()
                    .show();
        } else if (listener != null) {
            listener.onPositive();
        }
    }

    public interface OnPositiveListener {
        void onPositive();
    }
}
