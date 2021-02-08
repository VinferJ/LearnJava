package builder.model;



/**
 * @author Vinfer
 * @version v1.1
 * @date 2020-11-03    16:30
 * @description
 **/
public abstract class AbstractCarBuilderModel {

    /**
     * 汽车启动
     */
    public abstract void start();

    /**
     * 汽车停车
     */
    public abstract void stop();

    /**
     * 汽车鸣笛
     */
    public abstract void alarm();

}
