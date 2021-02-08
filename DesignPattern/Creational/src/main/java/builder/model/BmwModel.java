package builder.model;

/**
 * @author Vinfer
 * @version v1.1
 * @date 2020-11-03    15:59
 * @description
 **/
public class BmwModel extends AbstractCarModel{
    @Override
    public void start() {
        System.out.println("bmw has started...");
    }

    @Override
    public void stop() {
        System.out.println("bmw has stopped...");
    }

    @Override
    public void alarm() {
        System.out.println("bmw is alarming...");
    }
}
