package sort;

import java.util.Arrays;

/**
 * 排序算法 -- 选择排序
 * 选择排序包括两种：
 * 1.简单选择排序：
 *      从排序数据中按规定选出某个元素，再根据规定就换位置后达到排序的目的
 *      第i+1轮排序中，从第n-(i+1)个元素中寻找最小值，与第i+1个元素进行位置交换（i初值为0）
 *      平均时间复杂度为：O(n^2)，最差时间复杂度为：O(n^2)，最好时间复杂度为：O(n)
 *      需要额外空间为：O(1)，简单选择是一种不稳定的排序算法，n较小时适用
 * 2.堆排序（需要二叉树结构）：
 *
 * @author Vinfer
 * @date 2020-09-03    01:33
 **/
public class SelectingSort {

    public static void main(String[] args) {
        int[] arr = new int[80000];
        System.out.println("sorting array...");
        SwappingSort.generateRandomData(arr);
        long start = System.currentTimeMillis();
        //8w个数据的排序时间约为3s，比冒泡快很多
        simpleSelectSort(arr);
        System.out.println("sorting array cost about "+(System.currentTimeMillis()-start)+"ms");
        //System.out.println(Arrays.toString(arr));
    }

    public static void simpleSelectSort(int[] arr){
        int minIndex,min;
        /*只需要对len-1个数据进行排序，因为排到最后一个元素肯定是有序的*/
        for (int i = 0; i < arr.length-1; i++) {
            /*将i设定为最初的min值的下标*/
            minIndex = i;
            for (int j = i+1; j < arr.length; j++) {
                /*
                * 从剩余的len-(i+1)个元素中寻找最小值
                * 因为前面i个元素已被排好序
                * */
                if(arr[j]<=arr[minIndex]){
                    minIndex = j;
                }
            }
            /*如果最小值的下标不等于i，那么将最小值与第i个元素进行位置交换*/
            if (minIndex!=i){
                min = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = min;
            }
        }
    }

    public static void heapSort(int[] arr){

    }

}
