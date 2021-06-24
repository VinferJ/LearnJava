package search;

import sort.BucketSort;
import sort.SelectingSort;
import sort.SwappingSort;

import java.util.StringJoiner;

/**
 * @author vinfer
 * @date 2021-05-27 19:35
 */
public class BinarySearch {

    public static void main(String[] args) {
        int[] array = {1,-2,32,8,894,14,5,56,7,8,91,100};
        SwappingSort.quickSort(array,0,array.length - 1);
        System.out.println(array[binarySearch(array,8,0,array.length - 1)]);
    }

    public static int binarySearch(int[] array,int target,int start,int end){
        int mid = (start + end) / 2;
        // 起始索引非法或目标数值非法
        if(target < array[start] || target > array[end] || start > end){
            return -1;
        }
        // 向左二分
        if(target < array[mid]){
            return binarySearch(array,target,start,mid - 1);
        }
        // 向右二分
        else if(target > array[mid]){
            return binarySearch(array,target,mid + 1,end);
        }else{
            // 与中值相等
            return mid;
        }
    }


}
