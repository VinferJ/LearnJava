package lock;

/**
 * @author Jiang wenfa
 * @date 2021-07-14 16:59
 */
public class TestLatch {

    public static void main(String[] args) {
        testCycleBarrier();
    }

    static void testCountDownLatch(){
        CustomCountDownLatch countDownLatch = new CustomCountDownLatch(5);
        doTest(countDownLatch);
    }

    static void testCycleBarrier(){
        CustomCyclicBarrier cycleBarrier = new CustomCyclicBarrier(3);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName()+": start to wait lock...");
                cycleBarrier.await();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+": signal from await....");
            },"await-thread-"+(i+1)).start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static void doTest(Latch latch){
        long s = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                latch.await();
                System.out.println("release shared lock...");
                System.out.println((System.currentTimeMillis() - s)+"ms");
                try {
                    Thread.sleep(5 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 再次await，CountDownLatch会无效，因为CountDownLatch的计数器是一次性的
                latch.await();
                try {
                    Thread.sleep(10*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },"await-thread-"+(i+1)).start();
        }
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                System.out.println("doService....");
                latch.countDown();
            },"thread-"+(i+1)).start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
