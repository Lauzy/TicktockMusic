package com.freedom.lauzy.ticktockmusic.base;

/**
 * Desc : IPresenter
 * Author : Lauzy
 * Date : 2017/7/5
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface IPresenter<T extends IBaseView> {
    void attachView(T view);
    void detachView();
}
