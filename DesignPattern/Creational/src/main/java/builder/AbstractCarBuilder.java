package builder;

import builder.model.AbstractCarModel;

import java.util.ArrayList;

/**
 * @author Vinfer
 * @version v1.1
 * @date 2020-11-03    15:35
 * @description         通过传入一个创建顺序来建造对应的carModel
 **/
public abstract class AbstractCarBuilder {

    /**
     * 获取carModel
     * @return          {@link AbstractCarModel}
     */
    public abstract AbstractCarModel getCarModel();

    /**
     * 设置创建顺序
     * @param sequence  顺序集合
     */
    public abstract void build(ArrayList<String> sequence);

    public AbstractCarModel buildAndGet(ArrayList<String> sequence){
        this.build(sequence);
        return getCarModel();
    }

}
