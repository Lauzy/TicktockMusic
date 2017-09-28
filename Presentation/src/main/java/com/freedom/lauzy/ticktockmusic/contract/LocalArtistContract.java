package com.freedom.lauzy.ticktockmusic.contract;

import android.content.Context;
import android.widget.ImageView;

import com.freedom.lauzy.model.ArtistAvatar;
import com.freedom.lauzy.model.LocalArtistBean;
import com.freedom.lauzy.ticktockmusic.base.IBaseView;

import java.util.List;

/**
 * Desc : 本地歌手接口
 * Author : Lauzy
 * Date : 2017/9/28
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class LocalArtistContract {

    public interface Presenter {
        void loadLocalArtists();

        void loadArtistAvatar(String artistName, ImageView imageView);
    }

    public interface View extends IBaseView {

        Context getContext();

        void loadArtistResult(List<LocalArtistBean> artistBeen);

        void loadAvatarResult(String imgUrl, ImageView imageView);

        void emptyView();

        void loadFailed(Throwable throwable);
    }
}
