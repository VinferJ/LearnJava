package builder;

import builder.model.AbstractCarModel;
import builder.model.BmwModel;

import java.util.ArrayList;

/**
 * @author Vinfer
 * @version v1.1
 * @date 2020-11-03    16:02
 * @description
 **/
public class BmwBuilder extends AbstractCarBuilder{


    private final AbstractCarModel bmwModel = new BmwModel();

    @Override
    public AbstractCarModel getCarModel() {
        return this.bmwModel;
    }

    @Override
    public void build(ArrayList<String> sequence) {
        this.bmwModel.setSequence(sequence);
    }

}
