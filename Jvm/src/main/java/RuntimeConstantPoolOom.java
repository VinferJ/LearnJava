import java.util.HashSet;
import java.util.Set;

/**
 * @author vinfer
 * @date 2021-03-03 17:09
 */
public class RuntimeConstantPoolOom {

    public static void main(String[] args) {
        test();
    }

    static void oom(){
        // -XX:PermSize=6M -XX:MaxPermSize=6M
        // JDK6及之前的版本使用以上的jvm参数运行该方法，会出现OOM，
        // 因为intern方法会不断将新的字符串对象放到方法区（永久代）的常量池中
        // 而上面的参数就是限制了永久代的大小为6M，而short的范围足以让其发生OOM
        // 如果JDK版本大于6，无论是在JDK 7中继续使 用-XX：MaxPermSize参数或者在JDK 8及以上版本
        // 使用-XX：MaxMeta-spaceSize参数把方法区容量同 样限制在6MB，
        // 也都不会重现JDK 6中的溢出异常，循环将一直进行下去，永不停歇
        // 是因为自JDK 7起，原本存放在永久代的字符串常量池被移至Java堆之中，
        // 所以在JDK 7及以上版本，限制方法区的容量对该测试用例来说是毫无意义的。
        // 这时候使用-Xmx参数限制最大堆到6MB（-Xmx6M）就能够看到以下两种运行结果之一
        // 1. HashMap在扩容时的OOM   2.Integer在toString时的OOM
        Set<String> stringSet = new HashSet<>();
        short i = 0;
        while (true){
            stringSet.add(String.valueOf(i++).intern());
        }
    }

    static void test(){
        // 在JDK1.7之后，常量池已经移出了永久代，移到了堆区
        // StringBuilder对象一定是在堆区的，而intern方法会首次遇到的字符串（常量池中原本没有）的引用返回
        String str1 = new StringBuilder("计算机").append("软件").toString();
        // 这里会输出true
        // 首先"计算机软件"字符串有StringBuilder创建出已经放在堆区了，并且其引用已经赋值给了str1，
        // 常量池在堆区，因此intern()返回的引用也在堆区并且和由StringBuilder创建的那个字符串实例就是同一个
        // 所以intern方法得到的引用就是str1的，因此两者引用地址相等，输出true
        System.out.println(str1.intern() == str1);
        // java这个字符串在jvm启动后就已经预先存在于堆区的常量池了
        String str2 = new StringBuilder("ja").append("va").toString();
        // 这里会输出false
        // 这是因为“java”这个字符串在执行StringBuilder.toString()之前就已经出现过了，
        // 字符串常量池中已经有它的引用，不符合intern()方法要求“首次遇到”的原则，
        System.out.println(str2.intern() == str2);

        // 这段代码在JDK 6中运行，会得到两个false，产生差异的原因是，
        // 在JDK 6中，intern()方法会把首次遇到的字符串实例复制到永久代的字符串常量池中存储，
        // 返回的也是永久代里面这个字符串实例的引用，而由StringBuilder创建的字符串对象实例在 Java堆上，
        // 所以必然不可能是同一个引用，结果都会返回false。

    }

}
