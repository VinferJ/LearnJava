package array;

/**
 * @description         稀疏数组工具类
 * @author Vinfer
 * @date 2020-07-14  01:22
 **/
public class SparseArrays {

    /** 稀疏数组的列数*/
    private static final int LINE_NUM=3;


    /**
     * 将稀疏数组解压还原为原始二维数组
     * @param sparseArray           稀疏数组
     * @param compressionVal        压缩值
     * @return                      返回原始的二维数组
     */
    public static int[][] decompress(int[][] sparseArray, int compressionVal){
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
        if(compressionVal !=0){
            for (int i = 0; i < originArray.length; i++) {
                for (int j = 0; j < originArray[0].length; j++) {
                    originArray[i][j]=compressionVal;
                }
            }
        }
        for (int i = 1; i < sparseArray.length; i++) {
            //遍历非压缩值的行列值以及值
            sparseRow = sparseArray[i][0];
            sparseLine = sparseArray[i][1];
            sparseVal = sparseArray[i][2];
            //将该值赋值给原始二维数组
            originArray[sparseRow][sparseLine] = sparseVal;
        }
        return originArray;
    }

    /**
     * 将二维数组压缩为稀疏数组
     * @param towDimensionalArray           原始数组
     * @param compressionVal                压缩值
     * @return                              返回压缩结果
     */
    public static int[][] compress(int[][] towDimensionalArray,int compressionVal){
        int originLine = towDimensionalArray[0].length;
        int valueCount = 0;
        for (int[] ints : towDimensionalArray) {
            for (int j = 0; j < originLine; j++) {
                if (ints[j] != compressionVal) {
                    //与压缩值不等的值为存储值，此时存储值数量自增1
                    valueCount++;
                }
            }
        }
        //原始数组的行列值
        int row = towDimensionalArray.length;
        int line = towDimensionalArray[0].length;
        if((valueCount+1)*LINE_NUM<row*line){
            int[][] sparseArray = new int[valueCount+1][3];
            /*
             * 遍历首行数据，分别为：原数组行数，列数，非压缩值数量
             * 这3个值用于定义还原数组的行列大小
             * */
            sparseArray[0][0] = towDimensionalArray.length;
            sparseArray[0][1] = towDimensionalArray[0].length;
            sparseArray[0][2] = valueCount;
            //用于稀疏化的行列值
            int r=1,l=0;
            /*
             * 数组压缩
             * */
            for (int i = 0; i < sparseArray[0][0]; i++) {
                for (int j = 0; j < sparseArray[0][1]; j++) {
                    if(towDimensionalArray[i][j]!=compressionVal){
                        /*
                         * 匹配到不等于压缩值的值后，将该值的行列下标以及值
                         * 分别存储到改行的0,1,2号元素中
                         * */
                        sparseArray[r][l]=i;
                        sparseArray[r][l+1]=j;
                        sparseArray[r][l+2]=towDimensionalArray[i][j];
                        //将列数重置为0，重新由0赋值到2
                        l=0;
                        //进入下一行赋值
                        r++;
                    }
                }
            }
            return sparseArray;
        }else{
            throw new RuntimeException("\nthis array is not worth to be sparsified: " +
                    "origin size: "+line*row+" sparsify size: "+(valueCount+1)*3);
        }
    }


}
