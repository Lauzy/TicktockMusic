package com.freedom.lauzy.ticktockmusic.base;

/**
 * Desc : BasePresenter
 * Author : Lauzy
 * Date : 2017/7/3
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class BasePresenter<T extends IBaseView> implements IPresenter<T> {

    @Override
    public void attachView(T view) {

    }

    @Override
    public void detachView() {

    }
}
