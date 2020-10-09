/**
 * @author Vinfer
 * @date 2020-09-04    17:15
 **/
package list;

import java.util.Arrays;

class List{
    public static void main(String[] args) {
        int[] arr = new int[]{1,2,3,4};
        java.util.List list1 = Arrays.asList(arr);
        /*
         * asList中的方法参数是可变个数的泛型参数，
         * 当放入基本类型的数组时，会将该数组包装到/放到一个数组中（转变成了一个二维数组）再存到list中
         * 因此此时list的size就是1，只存了一个元素
         * 因为基本类型不支持泛型化，并且基本类型的数组不支持向下转型
         * */
        System.out.println(list1.size());
        System.out.println(list1);
        Integer[] arr2 = new Integer[]{1,2,3,4};
        java.util.List list2 = Arrays.asList(arr2);
        /*
         * asList传入的是一个对象类型数组，就会自动拆装箱
         * 将数组所有元素都拿出来放到list中，所以原数组有几个元素
         * list中就有几个元素
         * */
        System.out.println(list2.size());
        System.out.println(list2);

        /*
        *  public ArrayBlockingQueue(int capacity, boolean fair,
                              Collection<? extends E> c) {
        this(capacity, fair);

        final ReentrantLock lock = this.lock;
        lock.lock(); // Lock only for visibility, not mutual exclusion
        *
        * 这里加锁是为了防止指令重排，不是保证互斥性或线程安全，因为这里不存在并发安全问题
        * 禁止指令重排方式：volatile、加锁、final修饰
        * 而之所以在this初始化之后再加锁，是因为该队列元素存储的数组是final修饰的
        * 也不会被指令重排，因此不需要在this之前加锁
        *
        * */
    }
}