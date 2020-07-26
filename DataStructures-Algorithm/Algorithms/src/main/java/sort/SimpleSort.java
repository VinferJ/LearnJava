package sort;

import java.util.Arrays;

/**
 * @author Vinfer
 * @description         简单排序算法api接口
 * @date 2020-07-24  21:33
 **/
public class SimpleSort {


    /**
     * 冒泡排序
     * @param arr       被排序的整型数组
     */
    public static void bubbleSort(int[] arr){
        int len = arr.length;
        for (int i = 0; i < len-1; i++) {
            for (int j = 0; j < len - 1; j++) {
                if(arr[j]>arr[j+1]){
                    int temp = arr[j+1];
                    arr[j+1] =  arr[j];
                    arr[j] =  temp;
                }
            }
        }
    }

    public static void bubbleSort(Integer[] arr){
        int len = arr.length;
        for (int i = 0; i < len-1; i++) {
            for (int j = 0; j < len - 1; j++) {
                if(arr[j]>arr[j+1]){
                    int temp = arr[j+1];
                    arr[j+1] =  arr[j];
                    arr[j] =  temp;
                }
            }
        }
    }


}
