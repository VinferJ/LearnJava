package singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author Vinfer
 * @version v1.1
 * @date 2020-12-02    12:21
 **/
public class Test {

    public static void main(String[] args) throws Exception {
        Singleton instance = Singleton.getInstance();
        System.out.println(instance);
        Runnable task = new Task();
        for (int i = 0; i < 3; i++) {
            Thread t = new Thread(task);
            t.start();
        }
    }


    static class Task implements Runnable{

        @Override
        public void run() {
            try {
                Class<?> singletonClass = Class.forName("singleton.Singleton");
                Constructor<?> constructor = singletonClass.getDeclaredConstructor();
                constructor.setAccessible(true);
                Singleton invoke = (Singleton) constructor.newInstance();
                //Method newInstanceMethod = singletonClass.getDeclaredMethod("getInstance");
                //Singleton invoke = (Singleton) newInstanceMethod.invoke(singletonClass);
                System.out.println(Thread.currentThread().getName()+" get obj is: "+invoke);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
