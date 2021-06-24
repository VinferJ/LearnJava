package dianchu.pizza;

import java.util.Arrays;

/**
 * @author vinfer
 * @date 2021-03-10 16:10
 */
public class PizzaShop {

    private final Flavor[] flavors;

    public PizzaShop(Flavor... flavors){
        this.flavors = flavors;
    }

    public Pizza makePizza(Flavor flavor){
        if (isSupportedFlavor(flavor)){
            Pizza pizza = new Pizza();
            pizza.setFlavor(flavor);
            prepare(pizza);
            bake(pizza);
            slice(pizza);
            pack(pizza);
            return pizza;
        }
        return null;
    }

    private boolean isSupportedFlavor(Flavor flavor){
        return Arrays.stream(flavors).anyMatch(f -> f == flavor);
    }

    private void prepare(Pizza pizza){

    }

    private void bake(Pizza pizza){
    }

    private void slice(Pizza pizza){

    }

    private void pack(Pizza pizza){

    }

}
