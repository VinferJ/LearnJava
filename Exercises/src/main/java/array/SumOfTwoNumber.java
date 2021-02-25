package array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * leetcode
 * 两数之和：
 * 给定一个整数数组 nums和一个整数目标值 target，请你在该数组中找出和为目标值的那两个整数，并返回它们的数组下标。
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
 * 你可以按任意顺序返回答案。
 *
 * 输入：nums = [2,7,11,15], target = 9
 * 输出：[0,1] 因为 nums[0] + nums[1] == 9 ，返回 [0, 1]
 *
 * 解题思路：
 *  暴力解题：遍历元素，将元素和其他的每个元素相加一次，得到结果并返回
 *
 * @author Vinfer
 * @date 2021-02-25    00:06
 **/
public class SumOfTwoNumber {

    public static void main(String[] args) {
        int[] nums = {2,7,11,15};
        System.out.println(Arrays.toString(solution(nums, 9)));
        System.out.println(Arrays.toString(solution(nums, 26)));
        System.out.println(Arrays.toString(solutionOfHash(nums, 9)));
        System.out.println(Arrays.toString(solutionOfHash(nums, 26)));
    }

    public static int[] solution(int[] nums,int target){
        int[] result = new int[2];
        for (int i = 0; i < nums.length; i++) {
            for (int j = i+1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target){
                    result[0] = i;
                    result[1] = j;
                }
            }
        }
        return result;
    }

    public static int[] solutionOfHash(int[] nums,int target){
        int[] result = new int[2];
        Map<Integer,Integer> map = new HashMap<>(nums.length);
        // 使用Map可以将之前蛮力的时间复杂度O(n^2)降到O(n)
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])){
                result[0] = map.get(target - nums[i]);
                // i 就是 nums[i]的下标，nums[i] + target - nums[i] = target
                result[1] = i;
            }
            map.put(nums[i],i);
        }
        return result;
    }


}
