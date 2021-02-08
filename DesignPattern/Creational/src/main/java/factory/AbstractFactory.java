package factory;

import factory.products.AbstractBootProduct;
import factory.products.AbstractComputerProduct;

/**
 * @author Vinfer
 * @version v1.1
 * @date 2020-11-03    14:03
 * @description
 **/
public abstract class AbstractFactory {


    //有几个产品族，就应该有几个对应的创建方法

    /**
     * 创建由<code>{@link AbstractBootProduct}<code/>派生的产品
     * @param <T>   {@link AbstractBootProduct}
     * @return      {@link AbstractBootProduct}
     */
    public abstract <T extends AbstractBootProduct> T createBootProduct();

    /**
     * 创建由<code>{@link AbstractComputerProduct}<code/>派生的产品
     * @param <T>   {@link AbstractComputerProduct}
     * @return      {@link AbstractComputerProduct}
     */
    public abstract <T extends AbstractComputerProduct> T createComputerProduct();

}
