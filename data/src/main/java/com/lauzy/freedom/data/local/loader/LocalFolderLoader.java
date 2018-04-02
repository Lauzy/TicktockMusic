package com.lauzy.freedom.data.local.loader;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.freedom.lauzy.model.Folder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Desc : 文件夹
 * Author : Lauzy
 * Date : 2018/3/14
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class LocalFolderLoader {

    private static final String NUM_OF_SONGS = "music_num";
    private static final String[] PROJECTION = new String[]{MediaStore.Files.FileColumns.DATA,
            "count(" + MediaStore.Files.FileColumns.PARENT + ") as " + NUM_OF_SONGS};
    private static final String SELECTION = MediaStore.Audio.Media.IS_MUSIC + "=1 AND "
            + MediaStore.Audio.Media.DURATION + " > 45000 AND " + MediaStore.Audio.Media.TITLE
            + "!= '' " + ") GROUP BY (" + MediaStore.Files.FileColumns.PARENT;

    public static List<Folder> getMusicFolders(Context context) {
        Cursor folderCursor = getFolderCursor(context, PROJECTION, SELECTION, null);
        return queryLocalSongFolders(folderCursor);
    }

    private static List<Folder> queryLocalSongFolders(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        List<Folder> folders = new ArrayList<>();
        while (cursor.moveToNext()) {
            String filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
            int songCount = cursor.getInt(cursor.getColumnIndex(NUM_OF_SONGS));
            String folderPath = filePath.substring(0, filePath.lastIndexOf(File.separator));
            String folderName = folderPath.substring(folderPath.lastIndexOf(File.separator) + 1);
            Folder folder = new Folder();
            folder.folderName = folderName;
            folder.folderPath = folderPath;
            folder.songCount = songCount;
            folders.add(folder);
        }
        return folders;
    }

    //select _data,count(parent) as num_of_songs from file_table where (is_music=1 AND title != '') group by (parent);
    private static Cursor getFolderCursor(Context context, String[] projection, String selection, String[] paramArr) {
        return context.getContentResolver().query(MediaStore.Files.getContentUri("external"),
                projection, selection, paramArr, null);
    }
}
