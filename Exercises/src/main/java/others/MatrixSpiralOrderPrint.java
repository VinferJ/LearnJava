package others;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 顺时针打印矩阵：
 * 输入：[[1,2,3],[4,5,6],[7,8,9]]
 * 打印顺序应为：1 2 3 6 9 8 7 4 5
 *
 * @author Vinfer
 * @date 2021-02-24    16:59
 **/
public class MatrixSpiralOrderPrint {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int[][] matrix;
        int x = in.nextInt();
        int y = in.nextInt();
        matrix = new int[x][];
        for (int i = 0; i < x ; i++) {
            matrix[i] = new int[y];
            for (int j = 0; j < y; j++) {
                matrix[i][j] = in.nextInt();
            }
        }
        // 目前只能解决行数为3的矩阵，大于3的不行
        spiralOrderPrint(matrix);
    }

    static int counter = 0;
    static int total = 0;
    public static void spiralOrderPrint(int[][] matrix){
        int rowNum = matrix.length;
        int columnNum = matrix[0].length;
        total = rowNum * columnNum;
        int[] order = new int[total];
        // 先存放第一个元素
        order[counter++] = matrix[0][0];
        int level = 0;
        while (counter < total){
            // 按照顺时针的顺序：右 -> 下 -> 左 -> 上 为一个轮回
            int endRight = goRight(matrix, level, level, order, level);
            int endDown = goDown(matrix, level, endRight, order, level);
            int endLeft = goLeft(matrix, level + endDown, columnNum - 1 - level, order, level);
            int endUp = goUp(matrix, rowNum - 1 - level,endLeft + level,order, level);
            // 由于需要endUp的值来进行下一轮的开始，所以这里还需要再向右走一次
            goRight(matrix, endUp, level, order, level);
            level++;
        }
        System.out.println(Arrays.toString(order));
    }

    static int goRight(int[][] matrix,int x,int y,int[] order,int round){
        if (y+1 <= matrix[0].length - 1 - round){
            order[counter++] = matrix[x][y+1];
            if (counter >= total){
                return y+1;
            }
            return goRight(matrix, x,y+1,order,round);
        }else {
            return y;
        }
    }

    static int goDown(int[][] matrix,int x,int y,int[] order,int round){
        if (x+1 <= matrix.length - 1 - round){
            order[counter++] = matrix[x+1][y];
            if (counter >= total){
                return x+1;
            }
            return goDown(matrix,x+1,y,order,round);
        }else {
            return x;
        }
    }

    static int goLeft(int[][] matrix,int x,int y,int[] order,int round){
        if (y-1 >= round){
            order[counter++] = matrix[x][y-1];
            if (counter >= total){
                return y-1;
            }
            return goLeft(matrix, x,y-1,order,round);
        }else {
            return y;
        }
    }

    static int goUp(int[][] matrix,int x,int y,int[] order,int round){
        // 向上时不能回到原点
        if (x-1 >= round + 1){
            order[counter++] = matrix[x-1][y];
            if (counter >= total){
                return x-1;
            }
            return goUp(matrix,x-1,y,order,round);
        }else {
            return x;
        }
    }

}
