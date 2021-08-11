package lock;

/**
 * @author Jiang wenfa
 * @date 2021-07-14 20:41
 */
public interface Latch {

    void await();

    void countDown();

}
