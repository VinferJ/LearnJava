package sort;


/**
 * 排序算法 -- 交换排序
 * 交换排序有两种：
 *  1. 冒泡排序：
 *      遍历数组，在每一轮排序中，通过与前一个元素进行比较，让数值小的元素不断往前移（位置交换）
 *      直到所有元素都为有序
 *      平均时间复杂度为O(n^2),最差时间复杂度为O(n^2),最好时间复杂度为O(n)(一轮排完)
 *      需要额外的空间为O(1)(只需要一个中间变量)
 *      冒泡排序是一种稳定的排序，n较小时适合使用
 *  2. 快速排序：
 *      快速排序是对冒泡排序的一种改进，基本思想是：通过一趟排序将要排序的数据分割成独立的两部分
 *      其中一部分的所有数据都比另外一部分数据的所有数据要小然后再按此方法对这两部分数据分别进行
 *      快速排序，整个过程可以通过左右递归进行，达到有序，核心思想就是通过不断进行二分然后排序
 *      平均时间复杂度：O(N*log2N),最坏时间复杂度：O(n^2),最好时间复杂度：O(N*log2N)
 *      需要外空间：O(log2N)~O(N)，是一种不稳定的排序算法
 *      快速排序是典型的空间换时间的算法，并且在N较大时更适用
 *
 * @author Vinfer
 * @date 2020-07-24  21:33
 **/
public class SwappingSort {

    public static void main(String[] args) {
        int[] arr = new int[80000];
        generateRandomData(arr);
        long start = System.currentTimeMillis();
        System.out.println("sorting array...");
        //由于冒泡排序的的本质是一种交换排序，内部的比较、值交换会非常耗时
        //8w个数据排序的平均花费时间约为10s
        //bubbleSort(arr);
        System.out.println("bubble sort：sorting array cost about "+(System.currentTimeMillis() - start)+"ms");

        int[] arr2 = new int[8000000];
        System.out.println("sorting array...");
        generateRandomData(arr2);
        start = System.currentTimeMillis();
        /*
        * 快速排序就是典型的空间换时间的排序算法
        * 使用递归可以简化算法，在n越大时，对比其他排序算法更有优势
        * 800w元素的排序平均时间为1s（比希尔排序快将近1倍）
        * */
        quickSort(arr2, 0, arr2.length-1);
        System.out.println("quick sort：sorting array cost about "+(System.currentTimeMillis() - start)+"ms");
    }
    
    static void generateRandomData(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random()* (arr.length*100));
        }
    }

    /**
     * 冒泡排序
     * @param arr       被排序的整型数组
     */
    public static void bubbleSort(int[] arr){
        int len = arr.length;
        /*
        * 设置一个标志，判断某一轮排序中是否发生过交换
        * 如果没有，该标志是初值false
        * 如果出现了，那么将标志置为true，
        * 一轮排序中一次交换都没有意味着当前的数组以及是有序的
        * 因此可以提前结束循环
        * 那么增加一个标志变量，对空间的需求仍旧是O(1)
        * */
        boolean appearSwapping = false;
        for (int i = 0; i < len-1; i++) {

            /*
            * 由于每一轮排序中，后面的元素都是有序的
            * 即第n轮排序中，后面的n个元素都是已排序的
            * 因此就不在需要对他们再做多余的比较
            * 所以在第二层循环中的比较次数就应该是：len-1-i
            * len-1会多做很多次的多余的比较
            * */
            for (int j = 0; j < len-1-i; j++) {
                if(arr[j] > arr[j+1]){
                    appearSwapping = true;
                    int temp = arr[j+1];
                    arr[j+1] =  arr[j];
                    arr[j] =  temp;
                }
            }
            /*
            * 先判断是否发生过交换
            * 如果没有那么直接退出循环
            * */
            if(!appearSwapping){
                break;
            }else {
                /*
                * 如果出现过交换，那么将该标志变量重置为false
                * 为下一次判断做准备
                * */
                appearSwapping = false;
            }
        }
    }

    public static void bubbleSort(Integer[] arr){
        int len = arr.length;
        boolean appearSwapping = false;
        for (int i = 0; i < len-1; i++) {
            for (int j = 0; j < len-1-i; j++) {
                if(arr[j]>arr[j+1]){
                    int temp = arr[j+1];
                    arr[j+1] =  arr[j];
                    arr[j] =  temp;
                    appearSwapping = true;
                }
            }
            if(!appearSwapping){
                break;
            }else {
                appearSwapping = false;
            }
        }
    }

    /**
     * 快速排序
     * 由于需要用递归进行排序，所以需要传入左右边界值
     * @param arr       需要被排序的数组
     * @param l         数组左边界
     * @param r         数组右边界
     */
    public static void quickSort(int[] arr,int l,int r){
        /*拿到左右边界的副本*/
        int left = l;
        int right = r;
        int temp;
        //设置一个中轴值，以该值为左右划分的定界
        int pivot = arr[(left+right)/2];
        /*
        * 从左向右进行遍历
        * 目的是时循环完成后，可以让中轴值左边的值都小于中轴值
        * 让中轴值右边的值都大于中轴值
        * */
        while (left < right){
            /*
            * 寻找到一个大于pivot的值，准备将其交换到pivot的右边
            * */
            while (arr[left] < pivot){
                //如果arr[left]小于pivot，那么就让左边界下标右移1
                left += 1;
            }
            /*
            * 寻找到一个小于pivot的值，准备将其交换到pivot的左边
            * */
            while (arr[right] > pivot){
                //如果arr[right]大于pivot，那么就让右边界下标左移1
                right -= 1;
            }
            /*
             * 如果某一轮比交换后，左边界已经大于等于了右边界
             * 说明pivot左边的值已经全部小于pivot，右边的值已经全部大于pivot
             * 那么提前退出循环，不在进行下面的交换
             * */
            if(left >= right){
                break;
            }
            /*
            * 上面两个循环结束后，一定可以找到两个值：arr[left]和arr[right]
            * 并且arr[left]>pivot，arr[right]<pivot
            * 此时将两个值的位置进行交换
            * */
            temp = arr[left];
            //比pivot大的放到它的右边
            arr[left] = arr[right];
            //比pivot小的放到它的左边
            arr[right] = temp;
            /*
            * 如果交换完值之后，arr[left] == pivot,让右边界前移1
            * */
            if(arr[left] == pivot){
                right -= 1;
            }
            /*
            * 如果交换完值之后，arr[right] == pivot,让左边界后移1
            * */
            if(arr[right] == pivot){
                left += 1;
            }
        }
        /*
        * 如果交换值过后左右边界相等
        * 必须让左边界右移1，右边界左移1，否则会导致无限递归
        * */
        if(left == right){
            left += 1;
            right -= 1;
        }
        /*
        * 当初始的左边界（0）小于值交换过后的右边界值时
        * 说明中轴左边数据还没有完全有序，向左递归
        * */
        if(l < right){
            //向左递归应该传入初始的左边界和更新后的右边界
            quickSort(arr, l, right);
        }
        /*
        * 当初始的右边界值（arr.length-1）大于值交换后的左边界值时
        * 说明中轴右边的数据还没有完全有序，向右递归
        * */
        if(r > left){
            //向右递归应该传入初始的右边界和更新后的左边界
            quickSort(arr, left, r);
        }
    }


}
