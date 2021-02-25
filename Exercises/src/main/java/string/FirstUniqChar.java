package string;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个字符串，假设只有小写，找到它第一个不重复的字符并返回其索引，如果没有则返回-1
 * 例如：输入leetcode，应返回0
 *
 * @author Vinfer
 * @date 2020-12-23    23:58
 **/
public class FirstUniqChar {

    public static void main(String[] args) {
        String s = "iuhjhsafudi";
        System.out.println(solution1(s));
        System.out.println(solution2(s));
    }


    public static int solution1(String str){
        /*
        * 蛮力匹配，缺点：当输入字符串长度很大时，耗时会很长
        * */
        int len = str.length();
        int[] p = new int[len];

        for(int i = 0; i < len; i++){
            int count = 0;
            char ele = str.charAt(i);
            for(int j = 0; j < len; j++){
                if(str.charAt(j) == ele){
                    count += 1;
                    p[i] = count;
                }
            }
        }

        for(int k = 0; k < len; k++){
            if(p[k] == 1){
                return k;
            }
        }
        return -1;
    }

    public static int solution2(String str){
        /*
        * 凡是跟去重相关的题目都可以考虑使用Set或者Map来解题
        * 使用map的解题思路：
        *   首先第一次遍历字符串，使用map来保存每个字符在字符串中出现的次数
        *   第二次遍历通过在map中查询字符，如果值是1时，即只出现1次，那么将该key对应的char返回
        * */
        Map<Character,Integer> counter = new HashMap<>(str.length());
        int len = str.length();
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(i);
            // getOrDefault(ch,0) + 1 是个很关键的操作，当ch存在时，将其值再加一，否则其值为0+1=1
            // 如果第一次放入，那么值是1，如果不是第一次放入，那么值是重复放入的具体次数
            counter.put(ch, counter.getOrDefault(ch,0) + 1);
        }
        for (int i = 0; i < len; i++) {
            //再次遍历字符串，从map中查询，第一个查到的值为1的字符的索引即为所求
            if (counter.get(str.charAt(i)) == 1){
                return i;
            }
        }
        return -1;
    }

}
