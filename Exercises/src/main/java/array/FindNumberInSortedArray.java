package array;

/**
 * leetcode-剑指offer
 * 在排序数组中统计一个数字出现的次数
 *
 * 输入：nums = [5,7,7,8,8,10] target = 8
 * 输出：2
 * 输入：nums = [5,7,7,8,8,10] target = 6
 *
 * 解题思路：
 *  由于数组是已排序的，用二分法找到目标值，然后根据目标值下标寻找相同值
 *
 * @author Vinfer
 * @date 2021-02-24    23:22
 **/
public class FindNumberInSortedArray {

    public static void main(String[] args) {
        int[] nums = {5,7,7,8,8,10};
        System.out.println(solution(nums, 8));
        System.out.println(solution(nums, 5));
        System.out.println(solution(nums, 6));
    }

    public static int solution(int[] nums,int target){
        if (nums.length == 0){
            return 0;
        }
        // 通过二分查找来找到target值
        int left = 0,right = nums.length;
        int matchTime = 0;
        while (left <= right ){
            int mid = (left + right) / 2;
            if (mid >= 0 && mid <= nums.length-1){
                if (target > nums[mid]){
                    left = mid + 1;
                }else if (target < nums[mid]){
                    right = mid - 1;
                }else {
                    // 当前target值的下标是mid
                    int leftCount = mid;
                    int rightCount = mid+1;
                    // 寻找mid左边与target相等的数值
                    while (leftCount>=0 && nums[leftCount] == target){
                        matchTime++;
                        leftCount--;
                    }
                    // 寻找mid右边与target相等的数值
                    while (rightCount <= nums.length - 1 && nums[rightCount] == target){
                        matchTime++;
                        rightCount++;
                    }
                }
            }else {
                break;
            }
            if (matchTime > 0 ){
                break;
            }
        }
        return matchTime;
    }

}
