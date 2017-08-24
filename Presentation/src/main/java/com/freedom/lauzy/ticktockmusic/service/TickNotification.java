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
import android.support.v7.graphics.Palette;

import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.utils.ThemeHelper;

/**
 * Desc :
 * Author : Lauzy
 * Date : 2017/8/24
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class TickNotification {

    private static final int NOTIFY_ID = 0x0277;

    public static void buildNotification(Context context, MusicService musicService) {
        PendingIntent playIntent = createAction(context, MusicService.ACTION_PLAY);
        PendingIntent pauseIntent = createAction(context, MusicService.ACTION_PAUSE);
        PendingIntent previousIntent = createAction(context, MusicService.ACTION_LAST);
        PendingIntent nextIntent = createAction(context, MusicService.ACTION_NEXT);
//        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context, PlayDetailActivity.class), 0);

        boolean isPlaying = musicService.getPlaybackState().getState() == PlaybackState.STATE_PLAYING;
        MediaSession session = musicService.getMediaSession();
        MediaController controller = session.getController();
        if (controller.getMetadata() != null) {
            MediaDescription description = controller.getMetadata().getDescription();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setContentTitle(description.getTitle())
                    .setShowWhen(false)
                    .setSmallIcon(R.drawable.ic_notification)
                    .addAction(R.drawable.ic_skip_previous, "previous", previousIntent)
                    .addAction(isPlaying ? R.drawable.ic_pause : R.drawable.ic_play, "play pause",
                            isPlaying ? pauseIntent : playIntent)
                    .addAction(R.drawable.ic_skip_next, "next", nextIntent)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setOngoing(isPlaying)
                    .setAutoCancel(false)
                    .setLargeIcon(description.getIconBitmap())
                    .setContentText(description.getSubtitle())
                    .setStyle(new NotificationCompat.MediaStyle()
                            .setShowActionsInCompactView(0, 1, 2));
            if (description.getIconBitmap() != null) {
                int color = Palette.from(description.getIconBitmap()).generate()
                        .getMutedColor(ThemeHelper.getThemeColorResId(context.getApplicationContext()));
                builder.setColor(color);
            }
            Notification notification = builder.build();
            ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE))
                    .notify(NOTIFY_ID, notification);
        }
    }

    private static PendingIntent createAction(Context context, String action) {
        Intent intent = new Intent(context, MusicService.class);
        intent.setAction(action);
        return PendingIntent.getService(context, 1, intent, 0);
    }
}
