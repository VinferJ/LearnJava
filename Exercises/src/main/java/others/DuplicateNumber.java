package others;

import java.util.*;

/**
 * 浩鲸科技-2019-java开发题目1
 * 输入一个int型整数，按照从右向左的阅读顺序，返回一个不含重复数字的新的整数。
 * 解题思路：
 *  首先这里很明显涉及到去重，对于去重，如果是字符串，首先会想到Set或者Map去重
 *  但是这里要求有顺序地去重，并且输入也是数字，因此Set和Map都不能用
 *  由于这里是整数，而整数要一位一位地取出可以通过取余+除法来拿出数位
 *  所以可以每拿出一位，就保存到List中，保存时还要判断有没有已存在，就可以逆序并且去重保存了
 *  之所以可以直接就是逆序保存，因为取出数位时是从左往右取出的（num%10）
 *
 * 关于元素去重：
 *  如果不需要保证去重后的顺序可以使用Set或者Map
 *  如果需要保证去重后的顺序使用List，向List添加元素时判断List中是否已包含某个元素，如果没有才添加进去
 *
 *
 * @author Vinfer
 * @date 2021-02-24    12:05
 **/
public class DuplicateNumber {

    public static void main(String[] args) {
        // 牛客java语言的ACM模式
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            int num = in.nextInt();
            System.out.println(solution(num));
        }
    }

    public static int solution(int num){
        List<Integer> targetNums = new ArrayList<>();
        while (num != 0){
            // 将数值中的数位从个位一直向最高位取出，每取余10就会从右往左取出一个数位
            int digit = num % 10;
            // 如果存在相同的值那么不保存，保证可以去重
            // 而又由于数位是从右向左取出的，所以保存进去的顺序就是逆序的
            if (!targetNums.contains(digit)){
                targetNums.add(digit);
            }
            // 去除数值刚才取出的数位，每除于10就会从右往左去除一个数位
            num /= 10;
        }
        //
        StringBuilder resultBuilder = new StringBuilder();
        for (Integer targetNum : targetNums) {
            resultBuilder.append(targetNum);
        }
        return Integer.parseInt(resultBuilder.toString());
    }

}
