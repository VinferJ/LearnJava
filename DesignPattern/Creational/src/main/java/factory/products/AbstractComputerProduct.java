package factory.products;

/**
 * @author Vinfer
 * @version v1.1
 * @date 2020-11-03    14:17
 * @description
 **/
public abstract class AbstractComputerProduct {


    String productName;

    public String getProductName(){
        return productName;
    }

    public AbstractComputerProduct(String productName){
        this.productName = productName;
    }

    /**
     * 使用产品
     */
    public abstract void useProduct();


}
