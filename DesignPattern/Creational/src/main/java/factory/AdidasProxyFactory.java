package factory;

import factory.products.AbstractBootProduct;
import factory.products.AbstractComputerProduct;
import factory.products.Adidas;
import factory.products.MacBookPro;

/**
 * @author Vinfer
 * @version v1.1
 * @date 2020-11-03    14:12
 * @description
 **/
public class AdidasProxyFactory extends AbstractFactory{

    @Override
    public <T extends AbstractBootProduct> T createBootProduct() {
        @SuppressWarnings("unchecked")
        T adidas = (T) new Adidas("三叶草");
        return adidas;
    }

    @Override
    public <T extends AbstractComputerProduct> T createComputerProduct() {
        @SuppressWarnings("unchecked")
        T macbookPro = (T) new MacBookPro("苹果电脑");
        return macbookPro;
    }
}
