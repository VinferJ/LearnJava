package builder.model;

import java.util.ArrayList;

/**
 * @author Vinfer
 * @version v1.1
 * @date 2020-11-03    16:29
 * @description
 **/
public class AudiModel extends AbstractCarModel{

    @Override
    public void start() {
        System.out.println("audi has started...");
    }

    @Override
    public void stop() {
        System.out.println("audi has stopped...");
    }

    @Override
    public void alarm() {
        System.out.println("audi is alarming...");
    }

    static AudiCarModelBuilder audiCarModelBuilder = new AudiCarModelBuilder();

    static ArrayList<String> sequence = new ArrayList<>();

    static AudiModel audiModel = new AudiModel();

    /**
     * 通过内部声明一个builder对象，可以对对象进行更加精细控制的建造
     * 这样做的好处是可以大大精简代码的编写，提高可读性
     * 缺点是会产生很多被浪费掉对象
     * @return  {@link AudiCarModelBuilder}
     */
    public static AudiCarModelBuilder builder(){
        return audiCarModelBuilder;
    }

    public static class AudiCarModelBuilder{
        public AudiCarModelBuilder start(){
            sequence.add("start");
            audiModel.setSequence(sequence);
            return audiCarModelBuilder;
        }
        public AudiCarModelBuilder stop(){
            sequence.add("stop");
            audiModel.setSequence(sequence);
            return audiCarModelBuilder;
        }
        public AudiCarModelBuilder alarm(){
            sequence.add("alarm");
            audiModel.setSequence(sequence);
            return audiCarModelBuilder;
        }
        public AudiModel build(){
            return audiModel;
        }
    }

}
