package strategy;

/**
 * @author Vinfer
 * @version v1.3
 * @date 2020-10-09    10:54
 * @description
 **/
public interface BaseConditionFilter<T> {

    boolean filterByCondition(T t);

}
