package com.freedom.lauzy.ticktockmusic.function;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
@SuppressWarnings("unused")
public enum RxBus {

    INSTANCE;

    private FlowableProcessor<Object> mFlowableProcessor;
    private HashMap<String, CompositeDisposable> mDisposableHashMap;
    private Map<Class<?>, Object> mStickyEventMap;


    RxBus() {
        mFlowableProcessor = PublishProcessor.create().toSerialized();
        mStickyEventMap = new ConcurrentHashMap<>();
    }

    public void post(Object event) {
        synchronized (RxBus.class) {
            if (this.hasSubscribers()) {
                mFlowableProcessor.onNext(event);
            }
        }
    }

    public void postSticky(Object event) {
        synchronized (RxBus.class) {
            mStickyEventMap.put(event.getClass(), event);
        }
        post(event);
    }

    /**
     * 粘性事件
     *
     * @param type 类型
     * @return Flowable
     */
    private <T> Flowable<T> toStickyFlowable(final Class<T> type) {
        synchronized (RxBus.class) {
            Flowable<T> flowable = toFlowable(type);
            final Object event = mStickyEventMap.get(type);
            if (event != null) {
                return flowable.mergeWith(Flowable.just(type.cast(event)));
            } else {
                return flowable;
            }
        }
    }

    /**
     * 根据eventType获取Sticky事件
     *
     * @param eventType 类型
     * @return event
     */
    public <T> T getStickyEvent(Class<T> eventType) {
        synchronized (RxBus.class) {
            return eventType.cast(mStickyEventMap.get(eventType));
        }
    }

    /**
     * 移除指定eventType的Sticky事件
     *
     * @param eventType 类型
     * @return event
     */
    public <T> T removeStickyEvent(Class<T> eventType) {
        synchronized (RxBus.class) {
            return eventType.cast(mStickyEventMap.remove(eventType));
        }
    }

    /**
     * 移除所有的Sticky事件
     */
    public void removeAllStickyEvents() {
        synchronized (RxBus.class) {
            mStickyEventMap.clear();
        }
    }

    /**
     * 不指定类型，返回Object类型的Flowable对象
     *
     * @return Flowable对象
     */
    private Flowable<Object> toFlowable() {
        return toFlowable(Object.class);
    }

    /**
     * 根据传入对象类型返回对应类型的Flowable对象
     *
     * @param <T>  返回类型
     * @param type 传入类型
     * @return Flowable对象
     */
    private <T> Flowable<T> toFlowable(Class<T> type) {
        return mFlowableProcessor.ofType(type).onBackpressureBuffer();
    }

    private boolean hasSubscribers() {
        return mFlowableProcessor.hasSubscribers();
    }

    /**
     * 默认Disposable
     *
     * @param type  clazz
     * @param next  Consumer
     * @param error Consumer
     * @param <T>   泛型
     * @return Disposable
     */
    public <T> Disposable doSubscribe(Class<T> type, Consumer<T> next, Consumer<Throwable> error) {
        return toFlowable(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next, error);
    }

    public <T> Disposable doDefaultSubscribe(Class<T> type, Consumer<T> next) {
        return toFlowable(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next, Throwable::printStackTrace);
    }

    public <T> Disposable doStickySubscribe(Class<T> type, Consumer<T> next) {
        return toStickyFlowable(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next, Throwable::printStackTrace);
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
        removeAllStickyEvents();
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
