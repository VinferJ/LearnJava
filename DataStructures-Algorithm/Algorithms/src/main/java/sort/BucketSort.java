package sort;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 排序算法 -- 桶排序/基数排序
 * 基数排序也是一个经典的空间换时间的一个排序算法
 * 并且基数排序所需要的额外空间会非常大是O(n+r)
 * 三种情况的时间复杂度都是O(log_rB)，B是真数（0-9），r是基数：个十百千万...
 * 或者是O(d(n+r))
 * 并且基数排序是一种稳定的排序算法
 *
 * 当排序的数据中含有负数时，不建议使用基数排序，虽然可以实现负数的基数排序
 * 但是实现很复杂
 *
 * 基数排序需要的额外空间，一般每个桶的大小都会设置为与原数组大小一致
 * 当数据量非常大的时候比如一个亿的时候，会需要非常巨大的内存空间
 * (GB级别，如果是整型数组，每个桶大约0.37GB大小，排序需要固定的10个桶，那么总大小就是3.7GB)
 * 这个时候如果内存不足就会直接导致OOM
 * 所以可以说基数排序算是极限的空间换时间的排序，也是单线程内部排序中最快的排序算法
 * 如果用扩容机制又或者链表去代替原数组大小的桶的话，基数排序的性能就会大打折扣了
 *
 *
 * @author Vinfer
 * @date 2020-09-03    01:39
 **/
public class BucketSort {

    private static final int MAX_INT = Integer.MAX_VALUE;

    public static void main(String[] args) {
        int[] arr = {8,6,11,7,321,5,12,4,1000,100,10};
        sort(arr);
        System.out.println(Arrays.toString(arr));
        int[] arr2 = new int[1000 * 10000];
        SwappingSort.generateRandomData(arr2);
        long start = System.currentTimeMillis();
        System.out.println("sorting array...");
        /*
        * 基数排序的空间换时间的效果非常明显，对1000w数据的排序
        * 时间花费不到1s，在750ms左右
        * 但是对内存的消耗也是非常大的
        * */
        sort(arr2);
        System.out.println("sorting array cost about："+(System.currentTimeMillis() - start)+"ms");
    }

    public static void sort(int[] arr){
        int max = getMax(arr);
        //每次取位数时的被除数，初始为1
        int division = 1;
        /*
        * 初始化一个行数为10，长度为arr.length的二维数组
        * 相当于10个长度为arr.length的桶
        * */
        int[][] buckets = new int[10][arr.length];
        /*
        * 初始化一个数组，存放每个桶中存放的数组元素个数
        * 只有十个桶，因此该数组长度就是10
        * */
        int[] bucketEleCount = new int[10];
        //拿到最大值的有多少位数
        int round = (max+"").length();
        for (int x = 0; x < round; x++) {
            /*
             * 将数组中的元素依次取出
             * 从个位开始，一直取出元素的所有位数
             * 然后根据这个位数将取出的元素放到与位数对应的
             * 下标的桶中，如果元素最大的位数小于要取出的位数
             * 那么用0来代替，重复步骤知道最大值都不能再取出下一位
             * 即：个位 ==> 十位 ==> 百位 ==> ...
             * 按照这个顺序取出数位值，直到最大值都取尽了
             * */
            for (int ele : arr) {
                /*
                 * 如果要取出为数位大于数值最大的数位，用0代替
                 * 例如7，要取出十位以上的数位，都是0
                 * */
                int digitOfEle = (ele / division) % 10;
                /*
                 * 取出后，将该元素放入到与该数位对应的下标的桶中
                 * digitOfEle：
                 *   元素对应的具体的桶的下标（在哪个桶）
                 * bucketEleCount[digitOfEle]：
                 *   元素在某个桶中的具体位置的下标（在桶中的位置）
                 * */
                buckets[digitOfEle][bucketEleCount[digitOfEle]] = ele;
                //对应的桶的元素个数自增1，准备放置下一个元素
                bucketEleCount[digitOfEle]++;
            }
            /*
             * 元素放置完成后，再按顺序将桶中的元素取出并放回arr中
             * */
            //初始化arr的起始索引
            int eleIndex = 0;
            for (int i = 0; i < buckets.length; i++) {
                /*
                 * 如果bucketEleCount[i]是0的话，说明对应的桶中没有放置
                 * 任何元素，因此刻意跳过该桶
                 * 只有不为0时才将里面的元素取出放回原数组
                 * */
                if(bucketEleCount[i] != 0){
                    /*
                     * 遍历当前的桶，取出桶内所有元素放回元素组
                     * */
                    for (int j = 0; j < bucketEleCount[i]; j++) {
                        arr[eleIndex++] = buckets[i][j];
                    }
                    /*
                     * 将元素全部取出后要对该桶内的元素个数的计数清0
                     * */
                    bucketEleCount[i] = 0;
                }
            }
            /*
             * 更新数位的被除数
             * 每轮排序后都乘10，从而拿出下一个数位
             * */
            division *= 10;
        }
    }

    private static int getMax(int[] arr){
        //桶排序必须先找到最大值确认最大值的位数，因此这里也会产生部分耗时
        //这也说明了桶排序真正的排序速度非常之快
        int max = 0;
        for (int i : arr) {
            if(i >= max){
                max = i;
            }
        }
        return max;
    }

   public static void sortPlusVer(int[] arr){
        int max = getMax(arr);
        int round = (max + "").length();
        int division = 1;
        ArrayList<List<Integer>> buckets = new ArrayList<List<Integer>>();
       for (int i = 0; i < 10; i++) {
           /*
           * 使用指定大小的arraylist时，当数据量很庞大就会涉及扩容
           * 那么会让排序的性能大打折扣，失去空间换时间的本质
           * */
           buckets.add(new ArrayList<Integer>(10000));
       }
       for (int x = 0; x < round; x++) {
           for (int ele : arr) {
               int digitOfEle = (ele / division) % 10;
               buckets.get(digitOfEle).add(ele);
           }
           int eleIndex = 0;
           for (List<Integer> bucket : buckets) {
               if(!bucket.isEmpty()){
                   for (int i = 0; i < bucket.size(); i++) {
                       arr[eleIndex++] = bucket.remove(i);
                   }
               }
           }
           division *= 10;
       }
   }

}
