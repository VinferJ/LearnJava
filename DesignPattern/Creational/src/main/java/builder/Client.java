package builder;

import builder.model.AudiModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vinfer
 * @version v1.1
 * @date 2020-11-03    16:03
 * @description
 **/
public class Client {

    public static void main(String[] args) {
        /*
        * 建造模式的4个模式：
        *   - Product，产品类：这里的BenzModel和BmwModel都属于产品类
        *   - Builder，抽象建造者：这里的AbstractCarBuilder属性抽象建造者
        *   - ConcreteBuilder，具体建造者：这里的两个carModel的builder对象
        *   - Director，导演类：这里的BenzCarDirector
        *
        * */
        System.out.println("具体建造者模式：");
        concreteBuilder();
        System.out.println("导演类建造者模式：");
        director();
        System.out.println("链式建造者模式：");
        /*
        * 使用内嵌的builder进行对象的建造可以通过链式调用进行更精细控制对象的建造，但是会浪费额外的对象内存
        * */
        AudiModel build = AudiModel.builder().start().alarm().start().stop().alarm().build();
        build.run();
    }

    /**
     * 具体建造者，实现抽象类的所有方法，返回一组创建好的对象
     */
    static void concreteBuilder(){
        ArrayList<String> sq1 = new ArrayList<>();
        sq1.add("start");
        sq1.add("stop");
        sq1.add("alarm");
        ArrayList<String> sq2 = new ArrayList<>();
        sq2.add("start");
        sq2.add("alarm");
        sq2.add("alarm");
        sq2.add("stop");
        BenzBuilder benzBuilder = new BenzBuilder();
        //benz进行特定顺序创建后run
        benzBuilder.buildAndGet(sq1).run();
        System.out.println();
        BmwBuilder bmwBuilder = new BmwBuilder();
        //bmw进行特定顺序创建后run
        bmwBuilder.buildAndGet(sq2).run();
    }

    /**
     * 导演类中封装好所有组建顺序的对象，通过暴露的方法可以直接获取这些已组建好的对象
     * 调用者不需要传入组建顺序，只需要选择获取哪一种已建造好的对象即可
     */
    static void director(){
        CarDirector carDirector = new BenzCarDirector();
        carDirector.getCarModelA().run();
        System.out.println();
        carDirector.getCarModelB().run();
        System.out.println();
        carDirector.getCarModelC().run();
    }

}
