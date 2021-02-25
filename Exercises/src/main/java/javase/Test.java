package javase;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Vinfer
 * @date 2021-02-24    00:28
 **/
public class Test extends Base{


    public Test(){}



    public Test(int a){
        this();
        //super();
        //this()或super()只能2选1
    }

    public static void main(String[] args) {
        new Test();
        // 同一包下时，构造函数为default权限仍可访问
        new Base();
        map();
        try {
            System.out.println(func(0));
        }catch (Exception ex){
            /*
            * 由于异常在func方法中已被处理，因此在main中的catch不会在捕获该异常
            * */
            System.out.println("ex in main");
        }
        System.out.println("finished");
        // 只要进入了try块，并且try中抛出了异常，那么执行的顺序应该为：try -> catch -> finally
        try {
            System.out.println("try");
            throw new Exception();
        }catch (Exception ex){
            System.out.println("catch");
        }finally {
            System.out.println("finally");
        }
        string();
        System.out.println("==================================");
        int a = Integer.valueOf("1024").intValue();
        int b = Integer.parseInt("1024");
        System.out.println(a == b);
    }

    static void map(){
        Map<String,String> map = new HashMap<>(16);
        // HashMap非线程安全，可以允许null作为键和值
        // hashmap对key为null时会默认使用0作为key的哈希值，所以允许null作为key
        map.put(null, null);
        Map<String,String> table = new Hashtable<>();
        // HashTable线程安全，不允许null作为键，也不允许null作为值
        // 首先如果值为null，会直接抛出空指针异常，
        // 其次，hashtable不会判断key是否为null，直接就取key的hashcode，所以key也不可以为空
        table.put("key", "val");
        Map<String,String> tree = new TreeMap<>();
        // TreeMap非线程安全，不允许null作为键，允许null作为值
        // 当key为null时会直接抛出空指针异常
        tree.put("key", null);
        System.out.println(table);
        System.out.println(map);
        System.out.println(tree);
    }

    static void string(){
        String str1 = "str";
        String str2 = "str";
        String str3 = new String(str1);
        // == 是判断引用是否相等，str1指向的是字符串"str"的地址，而str3指向的str1对象所在的地址，所以这里一定是false
        System.out.println(str1 == str3);
        // str1和str2都指向相同的字符串"str"所在的内存地址，所以这里是true
        System.out.println(str1 == str2);
        // str1和str2的值和引用都相等，所以equals比较一定是true
        System.out.println(str1.equals(str2));
        // str1和str3的值相等，所以equals比较是true
        System.out.println(str1.equals(str3));
    }

    public static int func(int num) throws Exception {
        try {
            return num/10;
        }catch (Exception ex){
            // try中已经返回，所以会在返回前执行finally，但是不会再进入到catch中
            System.out.println("catch in func");
            throw new Exception("ex in func");
        }finally {
            System.out.println("finally");
        }
    }

}
