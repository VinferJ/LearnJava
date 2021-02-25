package array;

/**
 * leetcode-剑指offer
 * 0～n-1中缺失的数字：
 * 一个长度为n-1的递增排序数组中的所有数字都是唯一的，并且每个数字都在范围0～n-1之内。
 * 在范围0～n-1内的n个数字中有且只有一个数字不在该数组中，请找出这个数字。
 *
 * 例：[0,1,3]
 * 输出：2
 * 例：[0,1,2,3,4,5,6,7,9]
 * 输出：8
 *
 * 解题思路：如果该数列是完整的，那么意味着每个元素都和其数组下标相对应，因此缺失的值就是 nums[i] != i 时的 i
 * 排序数组中的搜索问题，首先想到 二分法 解决。
 * 根据题意，数组可以按照以下规则划分为两部分:
 *  左子数组：nums[i] = i;
 *  右子数组：nums[i] != i;
 *  缺失的数字等于 “右子数组的首位元素” 对应的索引；
 *
 *  除了二分法，还有更直观的方法：
 *  分情况讨论：
 *      1.根据提议，如果首元素是1的话，此时缺失的就是0，直接反回
 *      2.遍历整个nums，当nums[i] != i时，i就是所缺失的值（数列是递增的）
 *      3.如果遍历完得不到结果，以为着缺失的是数组最后一个元素的下一个元素的坐标，即数组的长度
 *
 * @author Vinfer
 * @date 2021-02-24    21:54
 **/
public class FindMissingNumber {

    public static void main(String[] args) {
        int[] nums = {0,1,2,3,4,5,6,7,9};
        System.out.println(solution(nums));
        System.out.println(dichotomySolution(nums));
    }

    public static int solution(int[] nums){
        if (nums[0]==1) {
            return 0;
        }
        for (int i = 0; i<nums.length; i++){
            if (nums[i]!=i) {
                return i;
            }
        }
        return nums.length;
    }

    public static int dichotomySolution(int[] nums){
        int i = 0, j = nums.length - 1;
        while(i <= j) {
            // 进行二分
            int m = (i + j) / 2;
            if(nums[m] == m) {
                // 目标元素在右半边元素，所以让左指针向右移动
                // 当nums[m] != m 时，此时的i就是缺失数值应该在的下标，也同时就是该缺失的数值
                i = m + 1;
            } else {
                j = m - 1;
            }
        }
        return i;
    }


}
