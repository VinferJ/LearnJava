package array;

import java.util.Arrays;

/**
 * @author Vinfer
 * @date 2020-07-13  21:01
 **/
public abstract class AbstractSparseArray<E> {

    /** 稀疏化后的二维数组 */
    private E[][] elements;

    /** 稀疏数组的列数 */
    private int lineNum;

    /** 稀疏数组的行数 */
    private int rowNum;

    /** 稀疏数组的压缩值 */
    private E duplicate;


    /**
     * 获取稀疏数组值
     * @return      返回一个稀疏数组
     */
    public E[][] getElements() {
        return elements;
    }

    /**
     * 设置稀疏数组的压缩值
     * @param duplicate     压缩值
     */
    public void setDuplicate(E duplicate) {
        this.duplicate = duplicate;
    }

    /**
     * 获取该稀疏数组的压缩值
     * @return      返回稀疏数组的压缩值
     */
    public  E getDuplicate(){
        return this.duplicate;
    }

    /**
     * 获取未压缩前的二维数组
     * @return          返回原二维数组
     */
    public E[][] getOrigin(){
        return revert(this.elements);
    }

    /**
     * 设置默认的压缩值
     * 当实例化稀疏数组时未指定压缩值时，使用默认的压缩值
     * @param originArray       原始二维数组
     */
    public abstract void defaultDuplicateSearch(E[][] originArray);

    /**
     * 还原稀疏数组
     * @param sparseArray       稀疏数组
     * @return                  返回原始二维数组
     */
    public abstract E[][] revert(E[][] sparseArray);

    /**
     * 稀疏化二维数组
     * @param originArray       原始二维数组
     */
    public abstract void sparsify(E[][] originArray);

    @Override
    public String toString() {
        return "AbstractSparseArray{" +
                "elements=" + Arrays.toString(elements) +
                ", lineNum=" + lineNum +
                ", rowNum=" + rowNum +
                ", duplicate=" + duplicate +
                '}';
    }
}
