package factory;

import factory.products.*;

/**
 * @author Vinfer
 * @version v1.1
 * @date 2020-11-03    14:30
 * @description
 **/
public class NikeProxyFactory extends AbstractFactory{

    @Override
    public <T extends AbstractBootProduct> T createBootProduct() {
        @SuppressWarnings("unchecked")
        T nike = (T) new Nike("耐克");
        return nike;
    }

    @Override
    public <T extends AbstractComputerProduct> T createComputerProduct() {
        @SuppressWarnings("unchecked")
        T surfaceBookPro = (T) new SurfaceBookPro("微软平板");
        return surfaceBookPro;
    }
}
