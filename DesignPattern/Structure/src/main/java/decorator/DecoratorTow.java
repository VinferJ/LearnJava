package decorator;

/**
 * @author Vinfer
 * @version v1.1
 * @date 2020-11-04    16:22
 * @description
 **/
public class DecoratorTow extends AbstractDecorator{

    public DecoratorTow(AbstractComponent component) {
        super(component);
    }

    @Override
    public void operate() {
        decoratorMethod();
        super.operate();
    }

    private void decoratorMethod(){
        System.out.println("decorate the component with method2...");
    }

}
