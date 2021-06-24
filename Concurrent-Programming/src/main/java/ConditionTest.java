import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author vinfer
 * @date 2021-06-20 0:52
 */
public class ConditionTest {

    static Integer start = 0;

    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        Thread thread = new Thread(() -> {
            lock.lock();
            try {
                while (start == 0) {
                    System.out.println("do awit.....");
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("end waiting.........");
                }
                System.out.println("break looped.........");
            } finally {
                lock.unlock();
            }
        }, "loop-thread");

        thread.start();

        Thread.sleep(2222);

        start = 1;

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("signal thread.........");
                condition.signal();
            } finally {
                lock.unlock();
            }
        }, "signal-thread").start();

        while (true){}
    }

}
