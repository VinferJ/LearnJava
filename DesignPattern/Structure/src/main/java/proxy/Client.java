package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @author Vinfer
 * @version v1.1
 * @date 2020-11-03    17:41
 * @description
 **/
public class Client {

    public static void main(String[] args) throws Throwable {
        final GamePlayer gamePlayer = new GamePlayer();
        //匿名函数的方式
        GenericProxyPlayer newProxyInstance = (GenericProxyPlayer) Proxy.newProxyInstance(Client.class.getClassLoader(), gamePlayer.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("执行前处理....");
                if (args != null && args.length == 2){
                    Object[] newArgs = new Object[args.length];
                    for (int i = 0; i < args.length; i++) {
                        newArgs[i] = args[i] + "_proxy";
                    }
                    Object result = method.invoke(gamePlayer,newArgs);
                    System.out.println("执行后处理...");
                    return result;
                }else {
                    return method.invoke(gamePlayer);
                }

            }
        });
        System.out.println("匿名函数生成代理对象：");
        newProxyInstance.play("1681321", "dsjfdsf");
        System.out.println(Proxy.isProxyClass(newProxyInstance.getClass()));
        //接口实现的方式
        GenericProxyPlayer proxyPlayer = (GenericProxyPlayer) new GamePlayerProxyHandler().getProxyInstance(gamePlayer);
        System.out.println("\n\n接口方式生成代理对象：");
        proxyPlayer.play("778612", "as4sg52sdf");
        proxyPlayer.login();
        proxyPlayer.logout();
        System.out.println(Proxy.isProxyClass(proxyPlayer.getClass()));
        //经管是通过不同方式生成的代理对象，但是实际上jdk为某个接口生成代理类时会保存该类，因此所有不管通过哪种方式得到的代理对象都是相同的
        System.out.println(newProxyInstance);
        System.out.println(proxyPlayer);
        /*
        * 上面的方式实现的代理对象，会对接口所有的方法都进行代理，即该代理对象调用任何方法都会执行代理方法
        * 因此在代理对象执行代理方法时非常有必要进行一些判断，即要求执行某些方法时才会执行代理逻辑
        * 这些方法除外的方法则不会执行
        * */

    }

    final void a(){
        System.out.println("sds");
    }

}
