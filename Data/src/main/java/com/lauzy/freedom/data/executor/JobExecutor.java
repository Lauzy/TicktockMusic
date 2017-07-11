package com.lauzy.freedom.data.executor;

import android.support.annotation.NonNull;

import com.freedom.lauzy.executor.ThreadExecutor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class JobExecutor implements ThreadExecutor {

    private ThreadPoolExecutor mThreadPoolExecutor;
    private static final int INITIAL_POOL_SIZE = 3;
    private static final int MAX_POOL_SIZE = 5;
    private static final int KEEP_ALIVE_TIME = 10;
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

    @Inject
    JobExecutor() {
        mThreadPoolExecutor = new ThreadPoolExecutor(INITIAL_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                new LinkedBlockingQueue<Runnable>(),
                new JobThreadFactory());
    }

    @Override
    public void execute(@NonNull Runnable command) {
        mThreadPoolExecutor.execute(command);
    }

    private static class JobThreadFactory implements ThreadFactory {
        private int counter = 0;
        private static final String THREAD_NAME = "Android_";

        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r, THREAD_NAME + counter++);
        }
    }
}
