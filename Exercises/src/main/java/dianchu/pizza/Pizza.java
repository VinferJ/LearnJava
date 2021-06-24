package dianchu.pizza;

/**
 * @author vinfer
 * @date 2021-03-10 16:11
 */
public class Pizza{

    private Flavor flavor;

    public void setFlavor(Flavor flavor) {
        this.flavor = flavor;
    }

    public Flavor getFlavor() {
        return flavor;
    }

    public PizzaType getType(){
        return flavor.getType();
    }

}
