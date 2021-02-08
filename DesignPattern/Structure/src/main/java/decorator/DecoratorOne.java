package decorator;

/**
 * @author Vinfer
 * @version v1.1
 * @date 2020-11-04    16:18
 * @description
 **/
public class DecoratorOne extends AbstractDecorator{

    public DecoratorOne(AbstractComponent component) {
        super(component);
    }


    /**
     * 重写父类的operate方法，对组件进行装饰
     */
    @Override
    public void operate() {
        //先执行装饰的方法，再执行父类方法
        decoratorMethod();
        super.operate();
    }

    /**
     * 用于装饰的方法要用private修饰，不对外暴露
     */
    private void decoratorMethod(){
        System.out.println("decorate the component with method1...");
    }

}
