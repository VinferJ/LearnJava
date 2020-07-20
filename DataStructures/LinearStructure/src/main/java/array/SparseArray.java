package array;

import java.util.*;

/**
 * @description     二维稀疏数组，将常规的多重复值的二维数组压缩为一个稀疏数组，可以节省存储空间
 * @author Vinfer
 * @date 2020-07-13  20:13
 **/
public class SparseArray{

    /** 稀疏化后的二维数组 */
    private int[][] elementsData;

    /** 原二维数组非压缩值个数 */
    private int valueCount;

    /** 稀疏数组的压缩值 */
    private Integer compressionVal=null;


    /**
     * 不指定压缩值的有参有参构造
     * @param towDimensionalArray       原始二维数组
     */
    public SparseArray(int[][] towDimensionalArray){
        //数组稀疏化
        sparsify(towDimensionalArray);
    }

    /**
     * 指定压缩值的有参构造
     * @param towDimensionalArray           原始二维数组
     * @param compressionVal                重复值
     */
    public SparseArray(int[][] towDimensionalArray,int compressionVal){
        //指定压缩值
        this.compressionVal = compressionVal;
        //数组稀疏化
        sparsify(towDimensionalArray);
    }

    public int getCompressionVal(){
        return this.compressionVal;
    }

    public int[][] getOrigin(){
        return this.decompress(this.elementsData);
    }

    /**
     * 默认压缩值匹配
     * @param towDimensionalArray       原始二维数组
     */
    public void defaultCompressionValMatch(int[][] towDimensionalArray){
        /*
        * 默认的压缩值匹配为重复次数大于等于数组总元素数量一半的元素值
        * 如果没有匹配到该值，那么将抛出一个自定义的异常
        * */
        int totalEle = towDimensionalArray.length*towDimensionalArray[0].length;
        int targetRepeatTimes = totalEle/2;
        /*
        * 利用HashMap key不重复的特性来统计一个数组中各个元素的重复次数
        * key存放数组元素值，value存放该key出现的次数
        * */
        HashMap<Integer,Integer> counterMap = new HashMap<>(totalEle);
        for(int[] ints:towDimensionalArray){
            for (int i = 0; i < towDimensionalArray[0].length; i++) {
                int key = ints[i];
                /*
                * 先检查是否包含该key值，如果有了，那么对该key的value增1
                * 否则新增该key，value为默认值1（该元素第一次出现）
                * */
                if(counterMap.containsKey(key)){
                    int val = counterMap.get(key);
                    counterMap.replace(key,val+1);
                }else{
                    counterMap.put(ints[i],1);
                }
            }
        }
        Collection<Integer> values = counterMap.values();
        ArrayList<Integer> valList = new ArrayList<>(values);
        //将重复次数值排序，排序后最大值为尾部元素
        Collections.sort(valList);
        int maxRepeatVal = valList.get(values.size()-1);
        /*
        * 如果最大的重复次数仍小于目标重复次数，说明匹配压缩值失败，此时抛出异常
        * */
        if(maxRepeatVal>=targetRepeatTimes){
            for(int key:counterMap.keySet()){
                int val = counterMap.get(key);
                if(val == maxRepeatVal){
                    /*
                     * 如果最大重复次数大于等于目标重复次数的元素
                     * 那么将该重复次数的值赋值给压缩值，将查找标记置为true并退出循环
                     * */
                    this.compressionVal =key;
                    break;
                }
            }
        }else{
            //没有匹配到压缩值，抛出异常
            throw new RuntimeException("unable to match the compression value, please set a compression value");
        }
    }

    /**
     * 初始化非压缩值的个数
     * @param towDimensionalArray       原始二维数组
     */
    private void setValueCount(int[][] towDimensionalArray){
        /*
        * 如果目标压缩值没有被设置，使用默认设置
        * */
        if(this.compressionVal == null){
            defaultCompressionValMatch(towDimensionalArray);
        }
        int originLine = towDimensionalArray[0].length;
        int valueCount = 0;
        for (int[] ints : towDimensionalArray) {
            for (int j = 0; j < originLine; j++) {
                if (ints[j] != this.compressionVal) {
                    //与压缩值不等的值为存储值，此时存储值数量自增1
                    valueCount++;
                }
            }
        }
        this.valueCount = valueCount;
    }

    /**
     * 判断当前二维数组是否值得稀疏化
     * @param valueCount        非压缩值数量
     * @param towDimensionalArray       原始二维数组
     * @return                  返回布尔值
     */
    private boolean isWorthToSparsify(int valueCount,int[][] towDimensionalArray){
        int row = towDimensionalArray.length;
        int line = towDimensionalArray[0].length;
        return (valueCount+1)*3<row*line;
    }

    /**
     * 将稀疏数组解压还原为原始二维数组
     * @param sparseArray           稀疏数组
     * @return                      返回原始的二维数组
     */
    private int[][] decompress(int[][] sparseArray){
        /*获取原始二维数组的行列值*/
        int row = sparseArray[0][0];
        int line = sparseArray[0][1];
        //初始化该二维数组
        int[][] originArray = new int[row][line];
        //稀疏数组中遍历的列数
        int l = 0;
        int sparseRow,sparseLine,sparseVal;
        /*
         * 如果压缩值不是0的时候，先遍历压缩值再遍历非压缩值
         * 防止处理0值元素的非覆盖
         * 如果压缩值是0，只需要遍历非压缩值
         * */
        if(this.compressionVal !=0){
            for (int i = 0; i < originArray.length; i++) {
                for (int j = 0; j < originArray[0].length; j++) {
                    originArray[i][j]=this.compressionVal;
                }
            }
        }
        for (int i = 1; i < sparseArray.length; i++) {
            //遍历非压缩值的行列值以及值
            sparseRow = sparseArray[i][l];
            sparseLine = sparseArray[i][l+1];
            sparseVal = sparseArray[i][l+2];
            //将该值赋值给原始二维数组
            originArray[sparseRow][sparseLine] = sparseVal;
            //将遍历列数重置为0，重新由0遍历到2
            l = 0;
        }
        return originArray;
    }

    /**
     * 将原二维数组压缩为稀疏数组
     * @param towDimensionalArray          原始二维数组
     */
    private void sparsify(int[][] towDimensionalArray){
        //初始化非压缩值数量，稀疏数组的行数为该数值+1
        setValueCount(towDimensionalArray);
        /*
        * 判断是否数组值得稀疏化
        * 如果不值得那么抛出异常
        * */
        if(isWorthToSparsify(this.valueCount,towDimensionalArray)){
            this.elementsData = new int[this.valueCount+1][3];
            /*
             * 遍历首行数据，分别为：原数组行数，列数，非压缩值数量
             * 这3个值用于定义还原数组的行列大小
             * */
            elementsData[0][0] = towDimensionalArray.length;
            elementsData[0][1] = towDimensionalArray[0].length;
            elementsData[0][2] = this.valueCount;
            int row=1,line=0;
            /*
             * 数组压缩
             * */
            for (int i = 0; i < elementsData[0][0]; i++) {
                for (int j = 0; j < elementsData[0][1]; j++) {
                    if(towDimensionalArray[i][j]!=this.compressionVal){
                        /*
                         * 匹配到不等于压缩值的值后，将该值的行列下标以及值
                         * 分别存储到改行的0,1,2号元素中
                         * */
                        elementsData[row][line]=i;
                        elementsData[row][line+1]=j;
                        elementsData[row][line+2]=towDimensionalArray[i][j];
                        //将列数重置为0，重新由0赋值到2
                        line=0;
                        //进入下一行赋值
                        row++;
                    }
                }
            }
        }else{
            throw new RuntimeException("\nthis array is not worth to be sparsified: " +
                    "origin size: "+towDimensionalArray.length*towDimensionalArray[0].length+" sparsify size: "+(this.valueCount+1)*3);
        }

    }

    /**
     * 遍历输出稀疏数组以及原始数组信息
     */
    public void display() {
        System.out.println("the sparse array elements:");
        for(int[] ints:this.elementsData){
            for (int i = 0; i < elementsData[0].length; i++) {
                System.out.print(ints[i]+" ");
            }
            System.out.println();
        }
        System.out.println("the compression element is: "+ getCompressionVal());
        System.out.println("\nthe origin array elements:");
        int[][] origin = getOrigin();
        for(int[] ints:origin){
            for (int i = 0; i < origin[0].length; i++) {
                System.out.print(ints[i]+" ");
            }
            System.out.println();
        }
    }
}
