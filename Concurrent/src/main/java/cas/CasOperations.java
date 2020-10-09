package cas;

import sun.misc.Unsafe;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Vinfer
 * @date 2020-08-23    12:38
 **/
public class CasOperations implements Serializable {

    /**
    * 初始化unsafe对象，提供cas操作
    * */
    private static Unsafe UNSAFE;
    private static long VALUE_OFFSET;

    static {
        try {
            /*
            * Unsafe类不开源，并不能直接提供给用户使用
            * 因此需要通过反射获取Unsafe对象实例
            *
            * */
            Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
            //获取unsafe的field实例
            Field field = unsafeClass.getDeclaredField("theUnsafe");
            //授权访问
            field.setAccessible(true);
            //获取真正的Unsafe实例
            UNSAFE = (Unsafe) field.get(null);
            //为值存储设置初始的对象变异量地址
            VALUE_OFFSET = UNSAFE.objectFieldOffset
                    (AtomicInteger.class.getDeclaredField("value"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
    * 值使用volatile修饰，保证可见性以及禁止指令重排(有序性)
    * */
    private volatile int val;

    public CasOperations(){
        this(0);
    }

    public CasOperations(int val){
        this.val = val;
    }

    /**
     * 获取当前值并且自增1
     * @return      返回当前值
     */
    public final int casGetAndAdd(){
        return UNSAFE.getAndAddInt(this, VALUE_OFFSET, 1);
    }

    public final void setVal(int val){
        this.val = val;
    }

    public final int getVal(){
        return val;
    }

    public boolean compareAndSet(int expectVal,int update){
        return UNSAFE.compareAndSwapInt(this, VALUE_OFFSET, expectVal, update);
    }




}
