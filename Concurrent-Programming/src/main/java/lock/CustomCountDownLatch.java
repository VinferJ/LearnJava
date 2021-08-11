package lock;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * CountDownLatch的逻辑：
 * 如果count大于0时，可以去获取共享锁，一旦count等于0，会唤醒所有获取了该共享锁的线程
 *
 * @author Jiang wenfa
 * @date 2021-07-14 15:46
 */
public class CustomCountDownLatch implements Latch{

    private static class Sync extends AbstractQueuedSynchronizer{

        Sync(int count){
            // 初始化计数器，将state设置为count，让count>0时会一直获取锁失败，从而park线程，
            // 直到计数器倒数到0的时候，去唤醒这些被park的线程
            setState(count);
        }

        /**
         * 获取当前还剩余可以加锁的数量
         * @return      当前还剩余可以加锁的数量
         */
        int getCount(){
            return getState();
        }

        @Override
        protected int tryAcquireShared(int arg) {
            //MY-NOTE tryAcquireShared方法
            // 这是AQS开放给子类去做前置操作的一个api，并且会以该结果作为到底会不会继续去执行内部排队获取锁的逻辑
            // AQS大部分调用该方法的形式都类似：if (tryAcquireShared(arg) < 0) { doAcquireShared(arg);  }
            // 上述逻辑中对tryAcquireShared(arg) 返回值语义已经被定义了，会有两种情况：
            //      1. 返回值大于0时，意味着尝试获取资源时，获取成功（cas修改state成功），不会再去排队获取锁，直接返回
            //      2. 返回值小于0时，意味着没有剩余资源了，获取资源失败，此时会调用doAcquireShared方法，进入等待获取锁的队列，去排队获取锁，直到获取成功
            // 例如通过tryAcquireShared方法内可以完成公平或非公平获取锁的

            //MY-NOTE
            // CountDownLatch本质就是一个共享锁，在初始化时会将state设置为count值，假设count是5
            // 每次countDown方法会使count-1，那么在count倒数到0之前，这里的getState的返回都是大于0，不等于0
            // 所以tryAcquireShared会返回-1，意味竞争锁失败，需要去排队，然后线程会被park住
            // 当有CountDownLatch的计时器倒数到0时，会去unpark队列中线程去获取锁，
            // 此时getState返回的一定是0，那么tryAcquireShared方法返回1，意味着竞争锁成功，方法直接返回，
            // 之前被await的线程都会继续执行
            // 所以这行代码要这样写的意义就是，count==0时都成功获取锁，不park线程，count>0时都获取锁失败，去排队，线程被park
            // 这也是CountDownLatch的功能：计时器倒计时未结束之前都要park线程，计时器倒计时结束后唤醒等待的线程
            // 具体的应用场景就是，调用者需要等待多个线程都执行完某些逻辑后才继续执行后续逻辑
            return getState() == 0 ? 1 : -1;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            //MY-NOTE 释放共享锁
            // CountdownLatch会在进行countdown之后，如果count值为0，就会去释放共享锁，这样就可以同时唤醒获取了这个共享锁的所有线程
            // 也就是会去唤醒count个线程，所以CountDownLatch的countDown方法实际上就是去尝试释放共享锁的过程
            // 同理这里的tryReleaseShared方法也是AQS对子类开放的API，当该方法返回true时，会去真正执行释放共享锁的逻辑
            // AQS大部分调用该方法的形式类似：if (tryReleaseShared(arg)){ doReleaseShared(); }
            // CountDownLatch是基于共享锁实现的，因此在该方法返回true去执行doReleaseShared方法时，而该方法会去循环唤醒队列中被park的线程
            // 如果是独自锁，只能唤醒队列中一个线程，其他线程会一直在队列中park住，无法被释放（CountDownLatch的计数器是一次性的）

            //MY-NOTE 用for循环是为了重复去尝试释放锁（cas修改state），直到释放成功
            for (;;){
                int currCount = getState();
                // 如果当前的state就是0，没有可以释放的锁
                if (currCount == 0){
                    return false;
                }
                // 每次来释放锁就让count-1，并且更新到AQS维护的state中
                int nextCount = currCount - 1;
                if (compareAndSetState(currCount,nextCount)){
                    // 当countdown之后，count为0时，去释放锁
                    return nextCount == 0;
                }
            }
        }
    }

    private final Sync sync;

    public CustomCountDownLatch(int count){
        if (count < 0){
            throw new IllegalArgumentException("count < 0");
        }
        this.sync = new Sync(count);
    }

    public int getCount(){
        return sync.getCount();
    }

    public void countDown(){
        sync.releaseShared(1);
    }

    public void await(){
        // 让调用await方法的线程进入等待状态，直到countdown至count=0
        //MY-NOTE 这里arg是为了传递个tryAcquireXXX方法，如果在重写的tryAcquireXXX方法中没有用上，那么这个值传什么都一样
        // 当前重写的tryAcquireShared方法中就没有用上，所以这里传任何一个int值都可以
        sync.acquireShared(99999);
    }

}
