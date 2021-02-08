package factory.products;

/**
 * @author Vinfer
 * @version v1.1
 * @date 2020-11-03    14:07
 * @description
 **/
public class Adidas extends AbstractBootProduct {


    public Adidas(String bootName){
        super(bootName);
    }

    @Override
    public void useProduct() {
        System.out.println("use product Adidas: "+getProductName());
    }
}
