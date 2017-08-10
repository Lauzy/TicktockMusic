package com.freedom.lauzy.ticktockmusic.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Desc : BasePresenter
 * Author : Lauzy
 * Date : 2017/7/3
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class BaseRxPresenter<T extends IBaseView> implements IPresenter<T> {

    private T mView;
    private CompositeDisposable mCompositeDisposable;

    protected void addDisposable(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    protected void dispose() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

    public T getView() {
        return mView;
    }

    @Override
    public void attachView(T view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
        dispose();
    }
}
