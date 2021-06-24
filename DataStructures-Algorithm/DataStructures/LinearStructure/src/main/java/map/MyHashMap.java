package map;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author vinfer
 * @date 2021-05-29 12:59
 */
public class MyHashMap {


    public static void main(String[] args) {

        int capacity = 11;
        // 如果不指定初始容量，那么默认的容量是16（1 << 4 = 2^4）
        // 如果指定了初始容量，那么初始容量会设置为大于该指定数值最靠近的一个2的幂次方值，例如大于11最靠近的是2的幂次方值是16，
        Map<Object,Object> map = new HashMap<>(capacity);

        //MY-NOTE HashMap使用位运算来计算数组index，位运算的效率远高于取模运算，因为位运算更加接近于计算机的运算方式
        // jdk8的index计算方式就是，先将key.hashCode进行高位运算得到一个hash值，及源码中的 (h = key.hashCode()) ^ (h >>> 16)
        // 这一操作的目的是为了桶的分布更加均匀，使分布更加散列，减少碰撞，增加空间利用率
        // 然后再用(当前容量-1)和得到的hash值进行位与运算
        // 为什么要用当前容量 - 1 去和hash运算呢？
        //  首先，由于容量是2^n，那么如果不减1去运算，将是如下所示：（上面的是hash的二进制，下面是容量的二进制，假设是16）
        //      0000 0011 0101 1101
        //      0000 0000 0001 0000
        //  那么上述的两个二进制数进行位于运算，只可能得到 16 || 0 的结果，因为下方的二进制只有一个1，其余都是0，
        //  那么就意味着结果肯定也有一个1，并且1的位置也一定和下面的二进制中的1的位置一样，所以结果只能是16或0，明显这是不合适的index
        //  但是如果将容量进行减1后，那么二进制就会进行退位，16-1=15，二进制为 0000 0000 0000 1111，用这个数再去运算：
        //      0000 0011 0101 1101
        //      0000 0000 0000 1111
        //  同样最终的结果也是由下面的二进制去决定的，最终结果的二进制，最多只能有4个1，并且位置一定是在最低的4位，因此结果一定会在0 ~ 15 之间，
        //  那么这个区间的值就刚刚好符合数组index的范围了，这也是为什么hashMap的容量一定要是2的幂次方的原因！！！！
        //  因为只有2的幂次方-1后，其二进制数才会进行退位，保证最低位全都是1，保证计算得到的index一定会落在 0 ~ 容量-1 的范围内
        String key = "body";
        int h,hash;
        hash = (h = key.hashCode()) ^ (h >>> 16);
        System.out.println(hash);
        System.out.println((capacity-1) & hash);

        //MY-NOTE Map在产生hash碰撞后，引入链表来解决该问题，在哈希表中该方法为拉链法/链地址法
        // 产生hash碰撞后，jdk7使用的是链表的头插，jdk8则是换成了链表尾插，并且还有红黑树转换的逻辑

        //MY-NOTE HashMap扩容机制
        // 1.根据threshold来进行扩容，threshold = capacity * loadFactor（默认为0.75）
        // 2.HashMap的size为当前存了多少的k-v，当size >= threshold 时，HashMap会进行一个2倍的扩容
        // 3.扩容后，会将旧的数组中的元素，通过再哈希的方式复制到新的数组中
        //  jdk7在数组元素复制过程中会存在的问题：
        //  1) 单线程下没有什么大问题，但是由于使用的是链表头插，链表被复制过来后会被倒置（旧数组从头结点开始遍历，新数组又进行头插，因此会倒置）
        //  2) 多线程下，由于transfer方法没有进行同步，因此有可能会造成链表成环的情况，最终导致无法在该链表上插入新元素（链表成环导致遍历链表时出现死循环）


        System.out.println(map);
        int j = 16;
        for (int i = 1; i <= 9 ; i++) {
            System.out.println("插入第 " + i + "个元素");
            map.put(j,i);
            j+=16;
        }

    }




}
