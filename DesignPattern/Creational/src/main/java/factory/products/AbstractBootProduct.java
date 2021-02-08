package factory.products;

/**
 * @author Vinfer
 * @version v1.1
 * @date 2020-11-03    13:59
 * @description
 **/
public abstract class AbstractBootProduct {

    String productName;

    public String getProductName(){
        return productName;
    }

    public AbstractBootProduct(String productName){
        this.productName = productName;
    }

    /**
     * 使用产品
     */
    public abstract void useProduct();

}
