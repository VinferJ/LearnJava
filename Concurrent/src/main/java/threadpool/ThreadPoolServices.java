package threadpool;

import sun.nio.ch.ThreadPool;

import java.util.concurrent.*;

/**
 * @author Vinfer
 * @date 2020-09-16    14:54
 **/
public class ThreadPoolServices {
    
    public static ExecutorService generateDefault(){
        return new ThreadPoolExecutor(10, 50, 0,
                TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(100),
                new ThreadPoolExecutor.DiscardOldestPolicy());
    }

    public static ExecutorService generateSelfDef(
            int corePoolSize, int maxPoolSize, BlockingQueue<Runnable> workQueue,RejectedExecutionHandler handler){
        return new ThreadPoolExecutor(corePoolSize, maxPoolSize,
                0, TimeUnit.SECONDS,workQueue,handler);
    }

    public static ExecutorService generateSelfDef(
            int corePoolSize, int maxPoolSize, BlockingQueue<Runnable> workQueue){
        return new ThreadPoolExecutor(corePoolSize, maxPoolSize,
                0, TimeUnit.SECONDS,workQueue,new ThreadPoolExecutor.DiscardOldestPolicy());
    }

    public static ExecutorService generateSelfDef(int corePoolSize, int maxPoolSize){
        return new ThreadPoolExecutor(corePoolSize, maxPoolSize,
                0, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(),new ThreadPoolExecutor.AbortPolicy());
    }

}
