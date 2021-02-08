package factory.products;

/**
 * @author Vinfer
 * @version v1.1
 * @date 2020-11-03    14:10
 * @description
 **/
public class MacBookPro extends AbstractComputerProduct {


    public MacBookPro(String sockName){
        super(sockName);
    }

    @Override
    public void useProduct() {
        System.out.println("use product MacBookPro: "+getProductName());
    }
}
