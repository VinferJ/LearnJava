package builder;

import builder.model.AbstractCarModel;

import javax.jws.Oneway;
import java.util.ArrayList;

/**
 * @author Vinfer
 * @version v1.1
 * @date 2020-11-03    16:23
 * @description
 **/
public class BenzCarDirector implements CarDirector{

    private final ArrayList<String> sequence = new ArrayList<>();

    @Override
    public AbstractCarModel getCarModelA() {
        //使用成员变量级别的集合在使用前一定要先clear，放置数据混乱
        this.sequence.clear();
        this.sequence.add("start");
        this.sequence.add("stop");
        this.sequence.add("alarm");
        BenzBuilder benzBuilder = new BenzBuilder();
        return benzBuilder.buildAndGet(this.sequence);
    }

    @Override
    public AbstractCarModel getCarModelB() {
        this.sequence.clear();
        this.sequence.add("start");
        this.sequence.add("alarm");
        this.sequence.add("stop");
        BenzBuilder benzBuilder = new BenzBuilder();
        return benzBuilder.buildAndGet(this.sequence);
    }

    @Override
    public AbstractCarModel getCarModelC() {
        this.sequence.clear();
        this.sequence.add("alarm");
        this.sequence.add("start");
        this.sequence.add("stop");
        BenzBuilder benzBuilder = new BenzBuilder();
        return benzBuilder.buildAndGet(this.sequence);
    }
}
