package tree.binary;

import sort.BucketSort;
import sort.SwappingSort;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * @author Vinfer
 * @date 2021-01-12    16:23
 **/
public class Test {

    public static void main(String[] args) throws CloneNotSupportedException {
        Integer[] data = {1,2,3,4,5,6,7,8,9,10};
        AvlTree<Integer> avlTree = AvlTree.build(data);
        System.out.println();
    }

    static void testSearch(int target){
        System.out.println("target val is: "+target);
        int len = 10000 * 10000;
        int[] data = new int[len];
        int[] array = new int[len];
        for (int i = 0; i < len; i++) {
            data[i] = new Random().nextInt(len);
            array[i] = new Random().nextInt(len);
        }
        data[len-1] = target;
        long s1 = System.currentTimeMillis();
        SwappingSort.quickSort(data, 0, len-1);
        long s2 = System.currentTimeMillis();
        System.out.println("快速排序1e数据耗费时间：" + (s2 - s1) + "ms");
        for (int i = 0; i < len; i++) {
            if (data[i] == target){
                System.out.println("found! val is: "+data[i]+", index is: "+i);
                break;
            }
        }
        long s3 = System.currentTimeMillis();
        System.out.println("普通线性查找时间："+(s3 - s2)+"ms");
        int middleIndex = (len - 1) / 2;
        int middle = data[middleIndex];
        System.out.println("middle val is: "+middle+", index is: "+middleIndex);
        int start = 0,end = 0;
        if (target > middle){
            start = middleIndex + 1;
            end = len - 1;
        }else if (target < middle){
            end = middle - 1;
        }else {
            System.out.println("found!");
        }
        for (int i = start; i <= end ; i++) {
            if (data[i] == target){
                System.out.println("found! val is: "+data[i]+", index is: "+i);
                break;
            }
        }
        long s4 = System.currentTimeMillis();
        System.out.println("二分查找时间："+ (s4 - s3) + "ms");
        int[] ints = Arrays.stream(array).parallel().sorted().toArray();
        long s5 = System.currentTimeMillis();
        System.out.println("并行流排序1e数据的耗时：" + (s5 - s4) + "ms");
        for (int i = 0; i < 100; i++) {
            if (i%10 == 0){
                System.out.println();
            }
            System.out.print(ints[i] + " ");
        }
    }

    static void testL(){
        AvlTree<Integer> avlTree = new AvlTree<>();
        avlTree.addChild(60);
        avlTree.addChild(50);
        avlTree.addChild(10);
        avlTree.addChild(9);
        avlTree.addChild(8);
        System.out.println(avlTree.getElements());
    }

    static void testR(){
        Integer[] elements = {1,2,3,4,5,6,7};
        AvlTree<Integer> avlTree = AvlTree.build(elements);
        System.out.println(avlTree.getElements());
        avlTree.deleteNode(4);
        System.out.println(avlTree.getElements());
    }

    static void testLr(){
        Integer[] elements = {8,10,15};
        AvlTree<Integer> avlTree = AvlTree.build(elements);
        avlTree.addChild(5);
        avlTree.addChild(6);
        System.out.println(avlTree.getElements());
        Integer[] elements2 = {8,10,5};
        AvlTree<Integer> avlTree2 = AvlTree.build(elements2);
        System.out.println(avlTree2.getElements());
    }

    static void testRl(){
        Integer[] elements = {5,10,15};
        AvlTree<Integer> avlTree = AvlTree.build(elements);
        avlTree.addChild(20);
        avlTree.addChild(19);
        System.out.println(avlTree.getElements());
        Integer[] elements2 = {10,12,15};
        AvlTree<Integer> avlTree2 = AvlTree.build(elements2);
        System.out.println(avlTree2.getElements());
    }


}
