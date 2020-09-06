package sort;

import java.util.Arrays;

/**
 * 排序算法 -- 插入排序
 * 插入排序：对需要排序的数据以插入的方式寻找该元素的适当位置，以达到排序目的
 * 插入排序包括两种：
 *  1.直接插入排序：
 *      将初始表看做一个有序表和无序表，有序表初始只有1个元素，无序表中有n-1个元素
 *      通过每次从无序表中取出一个元素插入到有序表的合适位置，直到有序表的元素个数为n
 *      平均时间复杂度：O(n^2)，最差时间复杂度：O(n^2)，最好时间复杂度：O(n)
 *      额外存储：O(1)，是一种稳定的排序算法，当大部分数据已排好时使用更好
 *  2.希尔排序：
 *      希尔排序是对直接插入排序的一种升级和优化，通过设定一个初始增量对数组进行分组
 *      组内的数据进行排序，排完一轮后增量缩小，直到变成1，全部数据可以排序完成
 *      思想类似于分治法+插入/交换排序，希尔排序的核心是将数据进行增量分组排序
 *      初始的增量是数组长度/2，得到每组数据都是两个（孤数不用管），然后组内排序
 *      后续的增量计算都是用上一个增量值再除于2得到
 *      增量计算的被除数一般用2，也可以是3，大于3后容易出错
 *      平均时间复杂度：O(n^s),1<s<2,最差时间复杂度：O(n^2),最好时间复杂度：O(n)
 *      额外空间：O(1)，是一种不稳定的排序算法
 *
 * @author Vinfer
 * @date 2020-09-03    01:33
 **/
public class InsertingSort {

    private static final int GROUP_LEN = 3;

    public static void main(String[] args) {
        int[] arr = new int[80000];
        System.out.println("directly insert sort：sorting array...");
        SwappingSort.generateRandomData(arr);
        long start = System.currentTimeMillis();
        //8w个元素排序平均是在500到600ms，不超过1s
        directlyInsertSort(arr);
        System.out.println("sorting array cost about "+(System.currentTimeMillis() - start)+"ms");
        System.out.println("shell sort：sorting array...");
        int[] arr2 = new int[4000000];
        SwappingSort.generateRandomData(arr2);
        start = System.currentTimeMillis();
        //希尔排序使用分治法对插入排序进行了优化，因此速度上会快很多
        //400w个元素的排序时间花费平均是在1s
        shellSort(arr2);
        System.out.println("sorting array cost about "+(System.currentTimeMillis() - start)+"ms");
    }


    public static void directlyInsertSort(int[] arr){
        int insertEle,insertIndex;
        for (int i = 0; i < arr.length-1; i++) {
            //有序表中待插入元素的位置下标
            insertIndex = i;
            //该插入元素是指无序表中的第一个元素
            insertEle = arr[insertIndex+1];
            /*
            * 寻找小于有序表中插入值的位置
            * 如果不满足条件，insertIndex会左移一个单位，
            * 并且有序表的元素会右移一个单位，直到找到插入位置
            * */
            while (insertIndex >=0 && insertEle < arr[insertIndex]){
                /*
                * 无序表中待插入的元素仍小于有序表中被插入元素
                * 此时让有序表的被插入位置元素右移一个单位，给最小值让出一个位置
                * 最终最小值可以在首位进行插入
                * 因为i是不断自增的，因此有序表中进行右移的元素总是在右端
                * 移动后的序列：首元素与第二个元素相等，从第二个元素开始是有序序列
                * */
                arr[insertIndex + 1] = arr[insertIndex];
                /*
                * 元素右移后，让有序表的待插入位置左移1个单位
                * 目的是让无序表中待插入元素与有序表的每个元素都可以进行比较
                * */
                insertIndex--;
            }
            /*
            * 因为最后满足while条件时insertIndex还会减1
            * 所以实际需要插入的位置是insertIndex+1
            * 如果实际插入位置就是i，意味着没有发生元素位移，不需要插入值
            * 只有不相等时才进行插入
            * */
            if(insertIndex+1 != i){
                arr[insertIndex+1] = insertEle;
            }
        }
    }

    public static void shellSort(int[] arr){
        int increment = arr.length/GROUP_LEN;
        int insertEle,insertIndex;
        while (increment>0){
            for (int i = increment; i < arr.length; i++) {
                /*
                * 这里的逻辑与直接插入排序基本一致
                * 因为i的初值是0+步长（增量）值，所以下面的index和ele都
                * 是应该是无序表中下标和对应的值（右边是无序表）
                * insertIndex：(无序表)待插入元素的下标
                * insertEle：（无序表）待插入的元素值
                * */
                insertIndex = i;
                insertEle = arr[insertIndex];
                /*
                * 为待插入元素寻找合适的位置，即当insertEle大于arr[insertIndex - increment]时找到
                * */
                while (insertIndex - increment >= 0 && insertEle < arr[insertIndex - increment]){
                    arr[insertIndex] = arr[insertIndex - increment];
                    //更新下标，比较下一个有序表中的元素，此处的更新需要减去步长值/增量值
                    insertIndex -= increment;
                }
                //插入元素
                arr[insertIndex] = insertEle;
            }
            //更新增量，更新为上一个增量除于组长的大小
            increment /= GROUP_LEN;
        }

    }


}
