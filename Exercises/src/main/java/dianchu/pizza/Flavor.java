package dianchu.pizza;

/**
 * @author vinfer
 * @date 2021-03-10 16:11
 */
public abstract class Flavor {

    private PizzaType type;

    public Flavor(PizzaType pizzaType){
        this.type = pizzaType;
    }

    public PizzaType getType() {
        return type;
    }
}
