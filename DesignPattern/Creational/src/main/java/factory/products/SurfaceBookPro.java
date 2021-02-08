package factory.products;

/**
 * @author Vinfer
 * @version v1.1
 * @date 2020-11-03    14:23
 * @description
 **/
public class SurfaceBookPro extends AbstractComputerProduct{

    public SurfaceBookPro(String sockName){
        super(sockName);
    }

    @Override
    public void useProduct() {
        System.out.println("use product SurfaceBookPro: "+getProductName());
    }

}
