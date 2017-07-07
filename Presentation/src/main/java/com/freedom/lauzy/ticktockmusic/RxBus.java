package com.freedom.lauzy.ticktockmusic;

import java.util.HashMap;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;

/**
 * Desc : RxBus
 * Author : Lauzy
 * Date : 2017/7/6
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public enum RxBus {

    INSTANCE;

    private FlowableProcessor<Object> mFlowableProcessor;
    private HashMap<String, CompositeDisposable> mDisposableHashMap;

    RxBus() {
        mFlowableProcessor = PublishProcessor.create().toSerialized();
    }

    public void post(Object event) {
        synchronized (RxBus.class) {
            if (this.hasSubscribers()) {
                mFlowableProcessor.onNext(event);
            }
        }
    }

    /**
     * 不指定类型，返回Object类型的Flowable对象
     *
     * @return Flowable对象
     */
    public Flowable<Object> toSubscriber() {
        return toSubscriber(Object.class);
    }

    /**
     * 根据传入对象类型返回对应类型的Flowable对象
     *
     * @param <T>  返回类型
     * @param type 传入类型
     * @return Flowable对象
     */
    public <T> Flowable<T> toSubscriber(Class<T> type) {
        return mFlowableProcessor.ofType(type).onBackpressureBuffer();
    }

    private boolean hasSubscribers() {
        return mFlowableProcessor.hasSubscribers();
    }

    /**
     * 默认Disposable
     * @param type clazz
     * @param next Consumer
     * @param error Consumer
     * @param <T> 泛型
     * @return  Disposable
     */
    public <T> Disposable doSubscribe(Class<T> type, Consumer<T> next, Consumer<Throwable> error) {
        return toSubscriber(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next, error);
    }

    public <T> Disposable doDefaultSubscribe(Class<T> type, Consumer<T> next) {
        return toSubscriber(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    /**
     * 保存订阅后的disposable
     *
     * @param o          obj
     * @param disposable disposable
     */
    public void addDisposable(Object o, Disposable disposable) {
        if (mDisposableHashMap == null) {
            mDisposableHashMap = new HashMap<>();
        }
        String key = o.getClass().getName();
        if (mDisposableHashMap.get(key) != null) {
            mDisposableHashMap.get(key).add(disposable);
        } else {
            CompositeDisposable compositeDisposable = new CompositeDisposable();
            compositeDisposable.add(disposable);
            mDisposableHashMap.put(key, compositeDisposable);
        }
    }

    /**
     * 取消订阅
     *
     * @param o obj
     */
    public void dispose(Object o) {
        if (mDisposableHashMap == null) {
            return;
        }
        String key = o.getClass().getName();
        if (!mDisposableHashMap.containsKey(key)) {
            return;
        }
        if (mDisposableHashMap.get(key) != null) {
            mDisposableHashMap.get(key).dispose();
        }
        mDisposableHashMap.remove(key);
    }
}
