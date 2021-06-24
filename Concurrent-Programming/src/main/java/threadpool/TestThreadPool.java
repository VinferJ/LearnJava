package threadpool;

import java.util.Calendar;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author vinfer
 * @date 2021-05-30 15:22
 */
public class TestThreadPool {

    static ReentrantLock lock = new ReentrantLock(true);

    public static void main(String[] args) {
        ThreadFactory factory = Executors.defaultThreadFactory();
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(10,factory);
        long s = Calendar.getInstance().getTimeInMillis();
        // 定时任务线程池
        scheduledExecutorService.schedule(() -> {
            System.out.println("exec....");
            System.out.println(Calendar.getInstance().getTimeInMillis()  - s);
            printThread();
        },2000, TimeUnit.MILLISECONDS);
        System.out.println("submitted......");
        if (!scheduledExecutorService.isShutdown()){
            scheduledExecutorService.shutdown();
        }

        //MY-NOTE   ThreadPoolExecutor的七个核心参数：
        //  long corePoolSize: 核心线程数
        //  long maximumPoolSize: 线程池中允许的最大线程数
        //  long keepAliveTime: 线程最大的允许不工作的时间
        //  TimeUnit unit: 时间单位
        //  BlockingQueue<Runnable> workQueue: 存放为来得及执行的任务队列
        //  ThreadFactory threadFactory: 创建线程的工厂
        //  RejectedExecutionHandler handler: 拒绝策略处理器
        //      AbortPolicy: 往外部抛异常
        //      CallerRunsPolicy: 如果当前线程池还未关闭，由当前提交任务的线程去执行任务
        //      DiscardPolicy: 丢弃当前提交的任务
        //      DiscardOldestPolicy: 丢弃任务队列中，最长时间未被执行的任务（入队时间最早未被执行的，即先执行一次poll操作，然后再offer该任务）

        //MY-NOTE   ThreadPoolExecutor对任务的执行顺序：
        //  corePool（核心线程） -> workQueue -> commonPool（非核心线程） -> rejectedHandler
        //  1.如果没有任何线程，那么创建核心线程，将任务交给核心线程执行，每来一个新任务都会创建一个核心线程去执行，直到创建的核心线程数等于corePoolSize
        //  2.如果已创建了corePoolSize个核心线程，将任务放到任务队列中（即使核心线程中有空闲的线程），核心线程会从任务队列中去取任务并执行
        //  3.如果是无界队列，直接放到任务队列中，如果是有界队列，查看容量是否已满，容量未满，也放入队列中
        //  4.如果任务队列已满，那么检查maximumPoolSize，如果还可以创建非核心线程，那么去创建并执行这些任务
        //  5.如果不能再创建非核心线程去执行任务，那么执行拒绝策略处理器
        //  tip: 非核心线程数 = maximumPoolSize - corePoolSize
        //       当线程池中核心线程创建满了才会进行线程的复用
        //       非核心线程当执行完任务后会被回收，不会进行复用，相当于临时工的概念

        //MY-NOTE   ThreadPoolExecutor如何实现线程复用或执行任务的方式:
        //  在内部的runWorker的方法中，通过一个while循环去实现不断地获取并执行任务
        //      while (task != null || (task = getTask()) != null) { ... }
        //  这里的task是初始化Worker对象时所绑定的firstTask任务，而getTask则是从任务队列中去获取任务
        //  而由于任务队列是一个阻塞队列，因此可以通过阻塞的取任务来保持线程的活动状态，即不马上回收线程，即时线程处于空闲状态
        //  在getTask方法中的一段核心代码体现了为什么非核心线程会在执行完任务（firstTask以及任务队列中的任务）后会被回收：
        //      boolean timed = allowCoreThreadTimeOut || wc > corePoolSize     （allowCoreThreadTimeout默认为false，可以手动设置）
        //      Runnable r = timed ? workQueue.poll(keepAliveTime, TimeUnit.NANOSECONDS) : workQueue.take()
        //  代码中的wc是workerCount，即该工人是第几个创建或添加的，如果wc大于corPoolSize，意味着这些线程是非核心线程，那么此时timed为true
        //  timed为true是使用的是超时的poll方法，也就是说如果keepAliveTime之后还是无法从任务队列中取到任务执行，
        //  那么对照上述的while循环，就不满足循环条件了，就会退出循环，此时就会将该线程进行回收
        //  而如果是核心线程去getTask会使用take方法进行阻塞（默认情况下），直到有任务取到然后不断执行，在线程池关闭之前都不会将线程进行回收（除非线程执行任务时被中断了）
        //  如果通过allCoreThreadTimeout方法去设置allowCoreThreadTimeOut为true时，那么所有线程（核心和非核心）都会在完成任务并且队列中无任务时，（poll超时获取仍返回null）线程会被回收
        //  如果希望核心线程也会在空闲时被回收，那么可以设置该值为true，但是一般不建议
        //  因此ThreadPoolExecutor实现空闲线程的维护和回收的最根本的机制就是通过阻塞队列BlockingQueue来实现的（ poll(long timeout) 以及 take()）
        //  tip: 如果在任务执行时往外抛出了异常，后面被提交的任务仍然可以正常执行，此时会将执行抛出异常的任务的Worker给移除，然后再去addWorker去执行后面的任务


        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
                1,1,0,TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(5),
                new ThreadPoolExecutor.AbortPolicy()
        );
        for (int i = 0; i < 5; i++) {
            poolExecutor.getQueue().offer(() -> {
                System.out.println("exec task："+Thread.currentThread().getName());
                throw new RuntimeException("failed........");
            });
        }
        for (int i = 0; i < 5; i++) {
            poolExecutor.execute(() -> {
                System.out.println("exec task："+Thread.currentThread().getName());
                throw new RuntimeException("failed........");
            });
        }
        System.out.println(15 & 536870911);



    }

    static int count = 0;

    static void printThread(){
        Thread current = Thread.currentThread();
        System.out.println(current.getName()+"  ------------  "+current.getState());
        System.out.println("count: "+(++count));
        System.out.println("==========================");

    }

}
