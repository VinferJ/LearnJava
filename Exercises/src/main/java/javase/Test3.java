package javase;

import java.util.*;

/**
 * @author vinfer
 */
public class Test3 {

    public static void main(String[] args) {
        int a = 10;
        int b = a + (a>>1);
        System.out.println(b);
        int size = 1000000;
        ArrayList<Integer> arrayList = new ArrayList<>(size);
        LinkedList<Integer> linkedList = new LinkedList<>();
        long s1 = System.currentTimeMillis();
        for (int i = 0; i < size-10; i++) {
            arrayList.add(i);
        }
        arrayList.add(null);
        long s2 = System.currentTimeMillis();
        for (int i = 0; i < size-10; i++) {
            linkedList.add(i);
        }
        linkedList.add(null);
        long s3 = System.currentTimeMillis();
        System.out.println(
                "arrayList: "+(s2 - s1)+"ms\n"+
                        "linkedList: "+(s3-s2)+"ms"
        );
        Set<String> linkedHashSet = new LinkedHashSet<>();
        Set<String> hashSet = new HashSet<>();
        linkedHashSet.add("hhks");
        linkedHashSet.add("jshd");
        linkedHashSet.add("jhsad");
        linkedHashSet.add("iouewb");
        linkedHashSet.add("8whakjs");
        hashSet.add("hhks");
        hashSet.add("jshd");
        hashSet.add("jhsad");
        hashSet.add("iouewb");
        hashSet.add("8whakjs");
        linkedHashSet.add(null);
        hashSet.add(null);
        for (String s : linkedHashSet) {
            System.out.println(s);
        }
        System.out.println("=====");
        for (String s : hashSet) {
            System.out.println(s);
        }
        Map<String,String> map = new HashMap<>(16);

    }

}
