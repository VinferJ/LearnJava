package decorator;

/**
 * @author Vinfer
 * @version v1.1
 * @date 2020-11-04    16:13
 * @description
 **/
public abstract class AbstractDecorator extends AbstractComponent{

    AbstractComponent component = null;

    public AbstractDecorator(AbstractComponent component){
        this.component = component;
    }

    /**
     * 这里对抽象组件的operate方法进行重载
     */
    @Override
    public void operate(){
        component.operate();
    }

}
