package factory.products;

/**
 * @author Vinfer
 * @version v1.1
 * @date 2020-11-03    14:24
 * @description
 **/
public class Nike extends AbstractBootProduct{

    public Nike(String bootName){
        super(bootName);
    }

    @Override
    public void useProduct() {
        System.out.println("use product Nike: "+getProductName());
    }

}
