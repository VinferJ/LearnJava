

import array.SparseArray;
import array.SparseArrays;
import org.junit.Test;
import java.util.Scanner;

/**
 * @author Vinfer
 * @date 2020-07-13  22:19
 **/
public class TestSparseArray {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("please set row and line number");
        int row=scanner.nextInt();
        int line=scanner.nextInt();
        int[][] array = new int[row][line];
        System.out.println("please init values");
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < line; j++) {
                array[i][j]=scanner.nextInt();
            }
        }
        SparseArray sparseArray = new SparseArray(array);
        sparseArray.display();
        System.out.println("\n====================================");
        int[][] sparse = {{6,7,8},{0,3,22},{0,6,15},{1,1,11},{1,5,17},{2,3,-6},{3,5,39},{4,0,91},{5,2,28}};
        int[][] origin = {{0,0,0,22,0,0,15},{0,11,0,0,0,17,0},{0,0,0,-6,0,0,0},{0,0,0,0,0,39,0},{91,0,0,0,0,0,0},{0,0,28,0,0,0,0}};
        int[][] decompress = SparseArrays.decompress(sparse, 0);
        int[][] compress = SparseArrays.compress(origin,0);
        System.out.println("\nsparse array decompress:");
        for (int[] ints:decompress){
            for (int i = 0; i < decompress[0].length; i++) {
                System.out.print(ints[i]+" ");
            }
            System.out.println();
        }
        System.out.println("\n====================================");
        System.out.println("\norigin array compress:");
        for (int[] ints:compress){
            for (int i = 0; i < compress[0].length; i++) {
                System.out.print(ints[i]+" ");
            }
            System.out.println();
        }
        /*
        * 验证数据
        * 原始二维数组
        0 0 0 22 0 0 15
0 11 0 0 0 17 0
0 0 0 -6 0 0 0
0 0 0 0 0 39 0
91 0 0 0 0 0 0
0 0 28 0 0 0 0
        *
        * 稀疏数组
        6 7 8
        0 3 22
        0 6 15
        1 1 11
        1 5 17
        2 3 -6
        3 5 39
        4 0 91
        5 2 28
        * */
    }


    @Test
    public void testSparseArr() {
        int[][] chessArr = new int[40][40];
        chessArr[1][2] = 1;
        chessArr[2][3] = 2;
        for (int[] row : chessArr) {
            for (int ele : row) {
                System.out.printf("%d\t", ele);
            }
            System.out.println();
        }
        int[][] compress = SparseArrays.compress(chessArr, 0);
        int[][] decompress = SparseArrays.decompress(compress, 0);
        for (int[] row : compress) {
            for (int ele : row) {
                System.out.printf("%d\t", ele);
            }
            System.out.println();
        }
        for (int[] row : decompress) {
            for (int ele : row) {
                System.out.printf("%d\t", ele);
            }
            System.out.println();
        }

    }


}
