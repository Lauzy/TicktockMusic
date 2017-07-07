package com.freedom.lauzy.executor;

import io.reactivex.Scheduler;

/**
 * Desc :
 * Author : Lauzy
 * Date : 2017/7/6
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface PostExecutionThread {
    Scheduler getScheduler();
}
