package com.freedom.lauzy.ticktockmusic.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaDescription;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.support.v7.app.NotificationCompat;

import com.freedom.lauzy.ticktockmusic.R;

/**
 * Desc :
 * Author : Lauzy
 * Date : 2017/8/24
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class TickNotification {

    private static final int NOTIFY_ID = 0x0277;
    private static NotificationManager mNotificationManager;

    public TickNotification(MusicService context) {
        mNotificationManager = ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE));
    }

    public void notifyPlay(MusicService context) {
        if (buildNotification(context) != null) {
            context.startForeground(NOTIFY_ID, buildNotification(context));
        }
    }

    public void notifyPause(MusicService context) {
        context.stopForeground(false);
        if (buildNotification(context) != null) {
            mNotificationManager.notify(NOTIFY_ID, buildNotification(context));
        }
    }

    public void stopNotify(MusicService context) {
        context.stopForeground(true);
        mNotificationManager.cancelAll();
    }

    private Notification buildNotification(MusicService musicService) {
        PendingIntent playIntent = createAction(musicService, MusicService.ACTION_PLAY);
        PendingIntent pauseIntent = createAction(musicService, MusicService.ACTION_PAUSE);
        PendingIntent previousIntent = createAction(musicService, MusicService.ACTION_LAST);
        PendingIntent nextIntent = createAction(musicService, MusicService.ACTION_NEXT);
//        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context, PlayDetailActivity.class), 0);

        boolean isPlaying = musicService.getPlaybackState().getState() == PlaybackState.STATE_PLAYING;
        MediaSession session = musicService.getMediaSession();
        MediaController controller = session.getController();
        if (controller.getMetadata() != null) {
            MediaDescription description = controller.getMetadata().getDescription();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(musicService);
            builder.setContentTitle(description.getTitle())
                    .setSmallIcon(R.drawable.ic_notification)
                    .setShowWhen(false)
                    .setOngoing(isPlaying)
                    .setContentText(description.getSubtitle())
                    .addAction(R.drawable.ic_skip_previous_notify, "", previousIntent)
                    .addAction(isPlaying ? R.drawable.ic_pause : R.drawable.ic_play, "",
                            isPlaying ? pauseIntent : playIntent)
                    .addAction(R.drawable.ic_skip_next_notify, "", nextIntent)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setStyle(new NotificationCompat.MediaStyle()
                            .setShowActionsInCompactView(0, 1, 2));
           /* if (description.getIconBitmap() != null) {
                Palette palette = Palette.from(description.getIconBitmap()).generate();
                int color = palette.getMutedColor(ThemeHelper.getThemeColorResId(musicService.getApplicationContext()));
                builder.setColor(color);
            }*/
            if (description.getIconUri() != null) {
                return builder.build();
            } else {
                builder.setLargeIcon(description.getIconBitmap());
                return builder.build();
            }
        }
        return null;
    }

    private PendingIntent createAction(Context context, String action) {
        Intent intent = new Intent(context, MusicService.class);
        intent.setAction(action);
        return PendingIntent.getService(context, 0, intent, 0);
    }
}
