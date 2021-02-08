package decorator;

import java.util.concurrent.AbstractExecutorService;

/**
 * @author Vinfer
 * @version v1.1
 * @date 2020-11-04    16:13
 * @description
 **/
public abstract class AbstractComponent {

    /**
     * 组件操作逻辑，有子类实现
     */
    public abstract void operate();

}
