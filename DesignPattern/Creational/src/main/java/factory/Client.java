package factory;

import factory.products.AbstractBootProduct;
import factory.products.AbstractComputerProduct;
import factory.products.Nike;

/**
 * @author Vinfer
 * @version v1.1
 * @date 2020-11-03    14:32
 * @description
 **/
public class Client {

    public static void main(String[] args) {
        AbstractFactory adi = new AdidasProxyFactory();
        AbstractFactory nike = new NikeProxyFactory();
        AbstractBootProduct adiBootProduct = adi.createBootProduct();
        AbstractComputerProduct adiComputerProduct = adi.createComputerProduct();
        adiBootProduct.useProduct();
        adiComputerProduct.useProduct();
        AbstractBootProduct nikeBootProduct = nike.createBootProduct();
        AbstractComputerProduct nikeComputerProduct = nike.createComputerProduct();
        nikeBootProduct.useProduct();
        nikeComputerProduct.useProduct();
    }

}
