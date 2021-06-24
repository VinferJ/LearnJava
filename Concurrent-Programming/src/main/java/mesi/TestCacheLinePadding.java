package mesi;

import java.util.Calendar;
import java.util.concurrent.CountDownLatch;

/**
 * @author vinfer
 * @date 2021-05-29 23:16
 */
public class TestCacheLinePadding {

    static class Line{
        private long a1,a2,a3,a4,a5,a6,a7;

        //MY-NOTE MESI缓存一致性协议以及CacheLine的应用
        // 首先，在cpu高速缓存中（三级缓存，L1,L2,L3）数据存取的基本单位是缓存行，即CacheLine，大小为64K
        // 也就是任何需要到cpu中进行运算（读写）的数据最终在高速缓存中都是以缓存行为单位来保存
        // MESI：英特尔CPU所遵循的缓存一致性协议，modified，exclusive，shared，invalid
        //  即当同一个缓存行中数据被多个线程进行读写时，如果有一个线程修改过了数据，那么需要去通知另一个线程被修改的最新数据，
        //  由此来保证数据的一致性
        //  volatile关键字的可见性在底层就是通过MESI以及缓存行锁来实现的

        //MY-NOTE
        //  由于数据是按照缓存行为单位来保存的，在Line中有一个long的变量param为8K，
        //  此时有一个Line[]数组，有两个元素，每个可以将其大小视作8K，而由于数组的内存又是连续分布的，
        //  因此两个元素很大概率都还在同一个缓存中进行保存，现在有两个线程t1和t2分别去同时修改这两个元素的param值
        //  由于MESI，因此修改param时会去通知其他也在操作当前缓存行的线程，但实际上被修改的值和当前线程修改的并不是一个值，
        //  仅是因为这两个值存在于同一个缓存行中，所以也去做相互通知，这就是伪共享的一个现象，这种通知是无效的没有意义的
        //  那么两个线程修改10e次param的耗时约为750ms左右
        //  当将上面和下面的long变量定义注释都打开时，此时一个Line对象的大小一定超过了64K，因此一个Line对象一定可以独占整个缓存行，
        //  那么两个元素的param变量也就一定是在不同的缓存行中，即param可以独占一个缓存行，因此此时两个线程修改的不是同一个缓存行的数据，不需要相互通知，
        //  也因此修改的效率会变高，最终耗时保持着250-400ms左右
        //  这就是缓存行对齐填充所带来的好处！！（避免伪共享）
        //  在单机版的消息队列框架Disruptor中就有一些类运用到缓存行的对齐填充
        public long param = 2L;

        private long b1,b2,b3,b4,b5,b6,b7;
    }

    static final long COUNT = 10_0000_0000L;

    public static Line[] arr = new Line[2];

    static {
        arr[0] = new Line();
        arr[1] = new Line();
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < COUNT; i++) {
                arr[0].param = i;
            }
            latch.countDown();
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < COUNT; i++) {
                arr[1].param = i;
            }
            latch.countDown();
        });
        long start = System.nanoTime();

        t1.start();
        t2.start();
        latch.await();

        long end = System.nanoTime();
        System.out.println(((end - start) / 100_0000) + "ms");
    }

}
