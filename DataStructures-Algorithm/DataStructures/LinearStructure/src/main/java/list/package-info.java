/**
 * @author Vinfer
 * @date 2020-09-04    17:15
 **/
package list;

import java.util.Arrays;

class List{
    public static void main(String[] args) {
        int[] arr = new int[]{1,2,3,4};
        java.util.List list1 = Arrays.asList(arr);
        /*
         * asList中的方法参数是可变个数的泛型参数，
         * 当放入基本类型的数组时，会将该数组包装到/放到一个数组中（转变成了一个二维数组）再存到list中
         * 因此此时list的size就是1，只存了一个元素
         * 因为基本类型不支持泛型化，并且基本类型的数组不支持向下转型
         * */
        System.out.println(list1.size());
        System.out.println(list1);
        Integer[] arr2 = new Integer[]{1,2,3,4};
        java.util.List list2 = Arrays.asList(arr2);
        /*
         * asList传入的是一个对象类型数组，就会自动拆装箱
         * 将数组所有元素都拿出来放到list中，所以原数组有几个元素
         * list中就有几个元素
         * */
        System.out.println(list2.size());
        System.out.println(list2);
    }
}