package com.freedom.lauzy.interactor;

import com.freedom.lauzy.executor.PostExecutionThread;
import com.freedom.lauzy.executor.ThreadExecutor;
import com.freedom.lauzy.utils.Preconditions;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Desc : 不同用例的抽象类，可指定业务逻辑
 * Author : Lauzy
 * Date : 2017/7/6
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public abstract class UseCase<T, Params>{

    private final ThreadExecutor mThreadExecutor;
    private final PostExecutionThread mPostExecutionThread;
    //RxPresenter 中控制注册及订阅，方便管理
//    private final CompositeDisposable mDisposable;

    UseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        mThreadExecutor = threadExecutor;
        mPostExecutionThread = postExecutionThread;
//        mDisposable = new CompositeDisposable();
    }

    /**
     * 设置线程调度，直接执行
     * @param observer DisposableObserver
     * @param params Params
     * @return Disposable
     */
    public Disposable execute(DisposableObserver<T> observer, Params params) {
        Preconditions.checkNotNull(observer);
        Observable<T> observable = this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.from(mThreadExecutor))
                .observeOn(mPostExecutionThread.getScheduler());
        return observable.subscribeWith(observer);
//        addDisposable(observable.subscribeWith(observer));
    }

    /**
     * 不进行线程调度的设置，直接返回 Observable，灵活控制
     * @param params 参数
     * @return Observable
     */
    public Observable<T> buildObservable(Params params) {
        return this.buildUseCaseObservable(params);
    }

    abstract Observable<T> buildUseCaseObservable(Params params);

    /* 取消了addDisposable 和 dispose，在业务层的 Presenter 中控制，更加灵活 */
    /*private void addDisposable(Disposable disposable) {
        Preconditions.checkNotNull(disposable);
        Preconditions.checkNotNull(mDisposable);
        mDisposable.add(disposable);
    }

    public void dispose() {
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }*/
}
