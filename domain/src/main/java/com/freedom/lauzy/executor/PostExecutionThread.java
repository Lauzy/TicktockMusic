package com.freedom.lauzy.executor;

import io.reactivex.Scheduler;

/**
 * Desc : 线程调度接口, useCase 观察者 observeOn 均在此线程中执行，
 * Presentation 中实现此接口，返回值为 Android 的 mainThread
 * Author : Lauzy
 * Date : 2017/7/6
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface PostExecutionThread {
    Scheduler getScheduler();
}
