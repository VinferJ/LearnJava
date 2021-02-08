package decorator;

/**
 * @author Vinfer
 * @version v1.1
 * @date 2020-11-04    16:15
 * @description
 **/
public class OriginalComponent extends AbstractComponent{

    @Override
    public void operate() {
        System.out.println("original component finishes the operation...");
    }
}
