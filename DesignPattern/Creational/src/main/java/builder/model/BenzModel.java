package builder.model;

/**
 * @author Vinfer
 * @version v1.1
 * @date 2020-11-03    15:57
 * @description
 **/
public class BenzModel extends AbstractCarModel{
    @Override
    public void start() {
        System.out.println("benz has started...");
    }

    @Override
    public void stop() {
        System.out.println("benz has stopped...");
    }

    @Override
    public void alarm() {
        System.out.println("benz is alarming...");
    }
}
