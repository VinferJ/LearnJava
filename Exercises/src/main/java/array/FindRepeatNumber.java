package array;

import java.util.*;

/**
 * leetcode-剑指offer
 * 找出数组中重复的数字
 * 在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。数组中某些数字是重复的，
 * 但不知道有几个数字重复了，也不知道每个数字重复了几次。请找出数组中任意一个重复的数字。
 *
 * 例：[2,3,1,0,2,5,3]
 * 输出：2 或 3
 *
 * 该题目延伸到数组中重复的字符或重复的字符串都可以用同样的思路去解决
 *
 * @author Vinfer
 * @date 2021-02-24    21:38
 **/
public class FindRepeatNumber {

    public static void main(String[] args) {
        int[] nums = {2,3,1,0,2,5,3};
        System.out.println(solution(nums));
    }

    public static int solution(int[] nums){
        // 通过向Set中移入数组的元素，如果在Set已包含该值，那么查找重复值成功，可以返回
        // 时间复杂度0(n),需要借助额外的空间，空间复杂度也是O(n)
        Set<Integer> tempList = new HashSet<>();
        for (int num : nums) {
            if (tempList.isEmpty() || !tempList.contains(num)){
                tempList.add(num);
            }else {
                return num;
            }
        }
        return 0;
    }

}
