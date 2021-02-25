package others;

import java.util.*;

/**
 * 浩鲸科技-2019-java开发题目2
 * 有n个学生站成一排，每个学生有一个能力值，牛牛想从这n个学生中按照顺序选取k名学生，
 * 要求相邻两个学生的位置编号的差不超过d，使得这k个学生的能力值的乘积最大，你能返回最大的乘积吗？
 * （能力值可包含负数值）
 *
 * 每个输入包含1个测试用例。每个测试数据的第一行包含一个整数n (1 <= n <= 50)，表示学生的个数，接下来的一行，包含n个整数，
 * 按顺序表示每个学生的能力值ai（-50 <= ai <= 50）。接下来的一行包含两个整数，k和d (1 <= k <= 10, 1 <= d <= 50)。
 * 示例：
 * 输入：
 * 3
 * 7 4 7
 * 2 50
 * 输出：49
 *
 * 正确的解题思路：动态规划
 *
 * @author Vinfer
 * @date 2021-02-24    13:10
 **/
public class MaxProduct {


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            int n = in.nextInt();
            int[] studentsAbility = new int[n];
            for (int i = 0; i < n; i++) {
                studentsAbility[i] = in.nextInt();
            }
            int k = in.nextInt();
            int d = in.nextInt();
            System.out.println(solution(n, studentsAbility, k, d));
        }
    }

    public static int solution(int n,int[] abilityVal,int k,int d){

        // 通过率60%

        List<Integer> targets = new ArrayList<>();
        List<Integer> results = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            targets.add(abilityVal[i]);
            for (int j = 0; j < n; j++) {
                if (j != i && targets.size() <= k - 1 ){
                    if ((j-i) <= d){
                        targets.add(abilityVal[j]);
                    }
                }
            }
            if (targets.size() == k){
                int sum = 1;
                for (Integer target : targets) {
                    sum *= target;
                }
                // 情况targets中的元素
                targets.clear();
                results.add(sum);
            }
        }
        return results.stream().max(Comparator.naturalOrder()).orElse(0);
    }

    public static void referenceAnswer(){
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        int[] ai = new int[n];
        for (int i = 0; i < ai.length; i++) {
            ai[i] = input.nextInt();
        }
        int k = input.nextInt();
        int d = input.nextInt();

        int[][] dp = new int[k][n];

        if (n >= 0) {
            System.arraycopy(ai, 0, dp[0], 0, n);
        }

        for (int i = 1; i < k; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j]=Integer.MIN_VALUE;
                if (j - d >= 0) {
                    for (int j2 = j - d; j2 < j; j2++) {
                        dp[i][j] = Math.max(dp[i][j], ai[j] * dp[i - 1][j2]);
                    }
                } else {
                    for (int j2 = 0; j2 < j; j2++) {
                        dp[i][j] = Math.max(dp[i][j], ai[j] * dp[i - 1][j2]);
                    }
                }
            }
        }

        int res = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            res = Math.max(res, dp[k - 1][i]);
        }
        System.out.println(res);
    }

}
