package com.freedom.lauzy.ticktockmusic.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

import com.freedom.lauzy.ticktockmusic.service.MusicService;

/**
 * Desc : 拔出耳机暂停
 * Author : Lauzy
 * Date : 2018/4/4
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class BecomingNoisyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action == null) {
            return;
        }
        switch (action) {
            case AudioManager.ACTION_AUDIO_BECOMING_NOISY:
                Intent musicIntent = new Intent(context, MusicService.class);
                musicIntent.setAction(MusicService.ACTION_PAUSE);
                context.startService(musicIntent);
                break;
        }
    }
}
