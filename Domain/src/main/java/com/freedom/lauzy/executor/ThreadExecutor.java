package com.freedom.lauzy.executor;

import java.util.concurrent.Executor;

/**
 * Desc : 线程执行接口，{@link com.freedom.lauzy.interactor.UseCase} 中使用，
 * 执行 useCase 可控制线程均在非 UI 线程。
 * Author : Lauzy
 * Date : 2017/7/6
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface ThreadExecutor extends Executor {
}
