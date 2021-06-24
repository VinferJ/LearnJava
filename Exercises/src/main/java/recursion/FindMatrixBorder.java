package recursion;

import java.util.Scanner;

/**
 * 点触科技2021春招笔试题：寻找矩阵边缘，1是障碍，0是可通行的
 *
 * @author vinfer
 * @date 2021-03-10 14:47
 */
public class FindMatrixBorder {

    static int path = 0;

    static int trace = 2;

    static int backTrace = 3;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        int n = scanner.nextInt();
        int[][] matrix = new int[m][];
        for (int i = 0; i < m; i++) {
            matrix[i] = new int[n];
            for (int j = 0; j < n; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }
        searchPath(matrix, 1, 1);
        printMatrix(matrix);
    }

    static boolean searchPath(int[][] matrix,int row,int column){
        if (isBorder(matrix, row, column)){
            // 如果边缘是barrier时，也将其置为trace，因为此时已经找到了边缘
            matrix[row][column] = trace;
            System.out.println("Matrix border has been found, the border point is ["+row+","+column+"]");
            return true;
        }else {
            if (matrix[row][column] == path){
                // 行进路劲记录为2
                matrix[row][column] = trace;
                // 开始走下一步，分别向前后左右各个方向以1为单位前进，
                // 如果遇到dead-end，那么进行回溯，回溯点记录为3
                // 向右移动一个单位
                if (searchPath(matrix, row, column + 1)){
                    return true;
                }
                // 向下移动一个单位
                else if (searchPath(matrix, row + 1, column)){
                    return true;
                }
                // 向左移动一个单位
                else if (searchPath(matrix, row, column - 1)){
                    return true;
                }
                // 向上移动一个单位
                else if (searchPath(matrix, row - 1, column)){
                    return true;
                }else {
                    // 如果4个方向都无法行进，意味着移到了dead-end，此时进行回溯，回溯点记录为3
                    matrix[row][column] = backTrace;
                    return false;
                }
            }else {
                // 当前点不是path即不可走，返回false让递归回溯到上一个点位
                return false;
            }
        }
    }

    static boolean isBorder(int[][] matrix,int row,int column){
        // 矩阵边缘的判别条件：x和y坐标中有一个的值为 0 或 len-1
        return (row == 0 || row == matrix.length - 1)
                || (column == 0 || column == matrix[0].length - 1);
    }

    static void printMatrix(int[][] matrix){
        for (int[] ints : matrix) {
            for (int anInt : ints) {
                if(anInt == trace || anInt == backTrace){
                    System.out.print(anInt+" ");
                }else{
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }

}
