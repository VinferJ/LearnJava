package sort;

import java.util.Arrays;

/**
 * 排序算法 -- 归并排序
 * 归并排序的用到了分治的思想，通过不断将数组元素二分，最终分到
 * 每组只有一个元素，开始归并，合并的次数等于元素个数-1
 * 合并思路：
 *      1. 每组只有一个元素，直接比较进行合并
 *      2. 每组拥有多个元素时，每组元素中都设置一个头指针
 *         并且开辟一个空间与初始数组大小一致的中间数组
 *         两个头指针元素进行比较，更小的进入到中间数组，
 *         并且所在组的头指针右移1位，以此类推，如果
 *         一组元素已经提前比较完，只剩另一组元素时，
 *         改组元素与自身剩余元素比较，按顺序进入中间数组
 * 归并排序也是典型的空间换时间的排序算法，需要额外空间为O(n)
 * 并且归并排序是稳定的排序算法，n较大的时候更适用
 * 平均时间复杂度：O(Nlog2N),最差时间复杂度：O(Nlog2N),最好时间复杂度：O(Nlog2N)
 * 所有情况下的时间复杂度都一样
 *
 * @author Vinfer
 * @date 2020-09-03    01:37
 **/
public class MergeSort {

    public static void main(String[] args) {
        int[] arr = new int[8000000];
        int[] temp = new int[arr.length];
        SwappingSort.generateRandomData(arr);
        System.out.println("sorting array...");
        long start = System.currentTimeMillis();
        /*
        * 归并排序是稳定的空间换时间的排序算法
        * 当n越大时，越适合
        * 800w的数据排序平均花费在1s
        * 而1000w的数据排序平均只有1350ms
        * */
        sort(arr,0, arr.length-1,temp);
        System.out.println("sorting array cost about；"+(System.currentTimeMillis() - start)+"ms");
    }

    public static void sort(int[] arr,int left,int right,int[] temp){
        /*
        * 通过递归进行数组分裂，并且在每次分裂完成后
        * 都进行merge操作
        * 从左向右分裂，因此判断条件用left>right
        * */
        if(left < right){
            //每次分裂的边界是mid值
            int mid = (left+right)/2;
            //向左进行递归分裂数组，那么截取端在右边
            sort(arr, left, mid, temp);
            //向右进行递归分裂，那么截取端在左边
            sort(arr, mid+1, right, temp);
            //当完全分裂后，由于递归的回调，会依次进行合并
            merge(arr, left, mid, right, temp);
        }
    }

    private static void merge(int[] arr,int left,int mid,int right,int[] temp){
        //初始化左边有序数组的起始索引
        int l = left;
        //初始化右边有序数组的起始索引
        int r = mid+1;
        //初始化中间数组的起始索引
        int t = 0;

        /*
        * 当两边的有序数组都有元素时，根据两端的指针
        * 比较两个元素，较小的一个进入temp数组中，然后
        * 该组的指针右移1位
        * */
        while (l <= mid && r<= right){
            if(arr[l] >= arr[r]){
                temp[t] = arr[r];
                //放进temp后，指针前移1位
                r++;
            }else {
                temp[t] = arr[l];
                //同理，放进temp之后指针要前移一位
                l++;
            }
            //temp中每放入一个元素，t都要前移一位
            t++;
        }

        /*
        * 完成上面的循环后，左边的数组有剩余元素时，
        * 由于剩下的数组就是有序的，因此直接将自身依次填充到temp数组中即可
        * */
        while (l <= mid){
            temp[t] = arr[l];
            l++;
            t++;
        }
        /*与上面同理，将右边数组剩余的元素依次放入temp数组中*/
        while (r <= right){
            temp[t] = arr[r];
            r++;
            t++;
        }

        /*
        * 当所有元素都放入temp中后
        * 需要将temp中的元素重新拷贝到arr中
        * 每次拷贝并不是拷贝所有，最后调用merge时
        * 发生的拷贝才拷贝所有元素
        * 假设n是4：一共需要拷贝3次
        * 对应的t和right应该是：
        *  第一次：0 -- 1
        *  第二次：2 -- 3
        *  第三次：0 -- 3
        * */
        //将t重新置为0
        t = 0;
        //初始化拷贝时的左起始索引
        int tempLeft = left;
        while (tempLeft <= right){
            arr[tempLeft] = temp[t];
            tempLeft++;
            t++;
        }

    }

}
