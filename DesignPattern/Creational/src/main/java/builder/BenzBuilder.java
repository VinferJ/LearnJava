package builder;

import builder.model.AbstractCarModel;
import builder.model.BenzModel;

import java.util.ArrayList;

/**
 * @author Vinfer
 * @version v1.1
 * @date 2020-11-03    16:00
 * @description
 **/
public class BenzBuilder extends AbstractCarBuilder{


    private final AbstractCarModel benzModel = new BenzModel();

    @Override
    public AbstractCarModel getCarModel() {
        return this.benzModel;
    }

    @Override
    public void build(ArrayList<String> sequence) {
        this.benzModel.setSequence(sequence);
    }
}
