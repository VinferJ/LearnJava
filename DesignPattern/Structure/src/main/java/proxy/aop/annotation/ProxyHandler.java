package proxy.aop.annotation;

import java.lang.annotation.*;

/**
 * @author Vinfer
 * @date 2021-01-25    10:16
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
public @interface ProxyHandler {

    String proxy() default "";

}
