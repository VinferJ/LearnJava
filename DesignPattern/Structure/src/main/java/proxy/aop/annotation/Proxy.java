package proxy.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Vinfer
 * @date 2020-12-23    18:04
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE,ElementType.METHOD,ElementType.FIELD})
public @interface Proxy {


    /*
    * AOP实现思路：
    *   1. 定义一个注解，用于识别原始/自定义代理类（ProxyHandler）
    *   2. 定义一个接口，用于最终代理类的生成
    *      最终代理类应包含这两个成员变量：1.ProxyHandler   2.ProxyObjectList
    *   3. 定义一个类似PointCutExpression的表达式逻辑，用于识别和扫描被代理对象，被代理对象应该是实体类或其中的方法
    *   4. 定义一个代理工厂类，用于生成代理对象
    *
    *
    * TODO  参数如何传递？JoinPoint？
    *
    * */



}
