package singleton;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * @author Vinfer
 * @date 2020-07-26  20:43
 **/
public class Singleton implements Serializable {

    /**
     * 要让该变量定义在Singleton instance之前
     * 如果是在其之后，会在初始化instance之后又保存了false的isInstantiated
     * 从而被覆盖导致该变量失去了原本的目的
     */
    private static volatile boolean isInstantiated = false;

    private static volatile Singleton instance;

    private Singleton(){
        /*
        * 通过添加一个实例化的标志标量来保护单例类被通过反射攻击而破坏
        * 当构造器被反射获取并设置为可访问后进行调用后，将标志置为true或者抛出异常
        * 保证即使是通过反射手段，也只能让调用者获取的是单例
        * */
        synchronized (Singleton.class){
            if (isInstantiated){
                throw new RuntimeException("This class is a singleton type, Don't try to instantiated it by reflection....");
            }else {
                isInstantiated = true;
            }
        }
    }

    public static Singleton getInstance() {
        //DLC：双重校验锁，保证懒汉式加载单例时，并发访问的安全，instance必须用volatile修饰，防止指令重排
        if (instance == null){
            synchronized (Singleton.class){
                if (instance == null){
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    private Object readResolve() throws ObjectStreamException {
        /*
        * 通过定义该方法，可以防止序列化攻击对实现了序列化接口的单例类的单例模式的破坏
        * 当实现了序列化接口时，当通过io流去读取对象时，是通过调用该方法来获得对象实例的
        * 如果类本身定义了该方法，并且将单例返回就可以解决序列化的破坏
        * 该方法的作用就是用readResolve()中返回的对象直接替换在反序列化过程中创建的对象。
        * 否则在流读取对象时会通过反射创建该对象，导致得到的实例和保存之前的不一样
        * */
        return instance;
    }


}
