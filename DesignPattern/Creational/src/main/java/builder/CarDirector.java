package builder;

import builder.model.AbstractCarModel;

/**
 * @author Vinfer
 * @version v1.1
 * @date 2020-11-03    16:21
 * @description
 **/
public interface CarDirector {

    AbstractCarModel getCarModelA();

    AbstractCarModel getCarModelB();

    AbstractCarModel getCarModelC();

}
