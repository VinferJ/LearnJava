package string;

/**
 * leetcode-剑指offer
 * 左旋转字符串
 * 字符串的左旋转操作是把字符串前面的若干个字符转移到字符串的尾部。请定义一个函数实现字符串左旋转操作的功能。
 * 比如，输入字符串"abcdefg"和数字2，该函数将返回左旋转两位得到的结果"cdefgab"。
 * 1 <= k <= 10000
 * 输入：abcdefg k=2
 * 输出：cdefgab
 *
 * 这道题多半是为了考察对String-API的熟练程度，直接用substring(k)+substring(0,k)即可
 * 当然，也可能会有限制使用该api的情况
 *
 * @author Vinfer
 * @date 2020-12-24    00:57
 **/
public class LeftSpinningString {

    public static void main(String[] args) {
        System.out.println(solution("abcdefg", 2));
        System.out.println(solution("lrloseumgh", 6));
        System.out.println(perfectSolution("abcdefg", 2));
        System.out.println(perfectSolution("lrloseumgh", 6));
    }

    public static String solution(String str,int k){
        if (k > str.length() - 1 || k < 0){
            return str;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = k; i < str.length(); i++) {
            builder.append(str.charAt(i));
        }
        for (int i = 0; i < k; i++) {
            builder.append(str.charAt(i));
        }
        return builder.toString();
    }

    public static String perfectSolution(String str,int k){
        return str.substring(k) + str.substring(0, k);
    }

}
