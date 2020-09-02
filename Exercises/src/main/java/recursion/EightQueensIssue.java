package recursion;

/**
 *
 * 八皇后问题 -- 回溯算法的经典应用
 * 问题描述：
 *      在一个8*8的棋盘上，摆放八个皇后，要求每个皇后之间无法相互攻击到
 *      即任意两个皇后不可以在同一列或同一排或者同一斜线上
 * 解题思路：
 *      1. 在第一行第一列，放置第一个皇后，
 *      2. 开始放置下一个皇后
 *      3. 判断当前放置的位置是否符合规则，
 *      4. 符合时进行放置，回到第二步
 *      5. 不符合放置时，寻找下一个位置，回到第二步
 *      6. 所有皇后放置完毕，得到第一个解，开始回溯，回退到上一个栈中
 *         就可以第一个皇后摆放在第一列的所有解都拿到
 *      7. 开始将第一个皇后放置在新的一列，重复2~6步直到获得所有解
 *
 *
 *
 * @author Vinfer
 * @date 2020-09-02    20:41
 **/
public class EightQueensIssue {

    private static final int QUEEN_NUM = 8;
    /**
     * 使用一维数组存放一组放置的解
     * 该一维数组中放置解的index对应了放置解的在棋盘中的行下标
     * 该放置解的值对应了放置解在棋盘中的列下标
     */
    private static final int[] RESULT_ARR = new int[QUEEN_NUM];
    private static int resolveCount = 0;

    public static void main(String[] args) {
        resolveEightQueensIssue();
        System.out.println("there are "+resolveCount+" solves for eight queens issue");
    }

    public static void resolveEightQueensIssue(){
        placeNextQueen(0);
    }

    private static void printResultArr(){
        resolveCount++;
        System.out.print("solve "+resolveCount+": ");
        for (int i : RESULT_ARR) {
            System.out.print(i+" ");
        }
        System.out.println();
    }

    private static void placeNextQueen(int rowIndex){
        /*
        * 如果行下标与皇后数相等，意味着8个皇后已经放置完成，已经拿到一组放置解
        * 此时对该组放置解进行打印并返回
        * */
        if(rowIndex == QUEEN_NUM){
            printResultArr();
            return;
        }
        /*
         * 使用一个循环遍历放置所有皇后
         * */
        for (int i = 0; i < QUEEN_NUM; i++) {
            /*
             * 先把第n个皇后，放置到该行的第一列中(i=0即第一列)，
             * 然后进行判断，放置下一个皇后
             * */
            RESULT_ARR[rowIndex] = i;
            /*
             * 对当前皇后放置的位置进行判断
             * 如果符合放置规则，那么放置下一个皇后
             * */
            if(placeable(rowIndex)){
                /*
                 * 放置下一个皇后，进入递归调用
                 * 当进入一个新的递归时，这个递归中又会有一个for循环作为放置逻辑处理
                 * 当一组放置解都求得后会在第一个if中返回
                 * 此时会回溯到上一个满足放置条件的皇后放置for循环逻辑中
                 * 又会在该循环中继续找到另外一个满足的放置位置
                 * 直到找到了所有的在第一个皇后放置在第n行第一列的所有放置解
                 * */
                placeNextQueen(rowIndex+1);
            }
            /*
             * 位置判断都不满足放置条件时
             * 回溯到上一个调用栈，此时该方法中的for循环会自增1
             * 并且rowIndex是上一个满足条件的皇后的行下标，是在第n行，在执行RESULT_ARR[rowIndex] = i; 时
             * 会将该皇后放置在下一列中，又开始新一组放置解的求解
             * */
        }
    }

    private static boolean placeable(int position){
        /*
        * 要与前面所有已放置的皇后进行判断
        * */
        for (int i = 0; i < position; i++) {
            /*
            * 由于每次传入的放置位置都是在下一行，因此不需要判断是否为同一行
            * 判断是否在同一列，值相等即在同一列：
            *   RESULT_ARR[i] == RESULT_ARR[position]
            * 判断是否在同一斜线上：
            *   Math.abs(position - i) == Math.abs(RESULT_ARR[position] - RESULT_ARR[i])
            *
            * */
            if(RESULT_ARR[i] == RESULT_ARR[position] ||
            Math.abs(position - i) == Math.abs(RESULT_ARR[position] - RESULT_ARR[i])){
                //存在冲突返回false
                return false;
            }
        }
        //都不冲突返回true
        return true;
    }

}
