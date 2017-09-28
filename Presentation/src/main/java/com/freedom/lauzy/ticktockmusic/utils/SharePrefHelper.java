package com.freedom.lauzy.ticktockmusic.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Desc :
 * Author : Lauzy
 * Date : 2017/9/4
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class SharePrefHelper {
    private static final String CONFIG_NAME = "ticktock_shared_prefs";
    private static final String REPEAT_MODE = "repeat_mode";
    private static final String SONG_ID = "song_id";
    private static final String ARTIST_SHARED_PREFS = "artist_avatar_prefs";

    private static SharedPreferences getSharePrefs(Context context) {
        return context.getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
    }

    private static SharedPreferences getArtistSharePrefs(Context context) {
        return context.getSharedPreferences(ARTIST_SHARED_PREFS, Context.MODE_PRIVATE);
    }

    public static void setRepeatMode(Context context, int mode) {
        getSharePrefs(context).edit().putInt(REPEAT_MODE, mode).apply();
    }

    public static int getRepeatMode(Context context) {
        return getSharePrefs(context).getInt(REPEAT_MODE, 0);
    }

    public static void setCurrentSongId(Context context, long songId) {
        getSharePrefs(context).edit().putLong(SONG_ID, songId).apply();
    }

    public static long getCurrentSongId(Context context) {
        return getSharePrefs(context).getLong(SONG_ID, 0);
    }

    public static void setArtistAvatar(Context context, String artistName, String avatarUrl) {
        getArtistSharePrefs(context).edit().putString(artistName, avatarUrl).apply();
    }

    public static String getArtistAvatar(Context context, String artistName) {
        return getArtistSharePrefs(context).getString(artistName, "");
    }
}
