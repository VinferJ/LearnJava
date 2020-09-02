package recursion;

/**
 * 迷宫问题 -- 递归/回溯的经典应用
 * 问题描述
 *      在一个n*n的迷宫中，从一个给定的起点
 *      走到唯一或唯一的出口
 *      其中2表示行进轨迹，1表示障碍物，3表示回溯路径
 *      0表示可通过路径，4表示终点
 *
 * @author Vinfer
 * @date 2020-09-02    20:45
 **/
public class MazeIssue {

    private static final int[][] DEFAULT_MAZE_MAP = {
            {1,1,1,1,1,1,4,1,1,1,1},
            {1,0,0,0,0,1,0,0,0,1,1},
            {1,1,1,0,0,0,1,1,0,0,4},
            {1,0,0,0,1,0,0,1,0,0,1},
            {1,0,1,0,1,0,0,1,0,1,1},
            {1,0,1,0,1,1,0,0,0,0,1},
            {1,0,1,1,0,0,0,1,0,1,1},
            {1,0,0,0,0,1,1,0,0,1,1},
            {1,1,1,1,1,1,1,1,1,1,1},
    };

    private static final int TRACE = 2;
    private static final int PATH = 0;
    private static final int BACK_TRACE = 3;
    private static final int END_POINT = 4;

    public static void main(String[] args) {
        System.out.println("original maze map: ");
        printMazeMap(DEFAULT_MAZE_MAP);
        resolveMazeIssue(DEFAULT_MAZE_MAP, null);
        System.out.println("resolved maze map: ");
        printTrace(DEFAULT_MAZE_MAP);
    }

    public static int[][] loadMapFromDisk(String filePath){
        //TODO  通过本地io，从磁盘中加载迷宫地图
        return null;
    }

    public static void printMazeMap(int[][] mazeMap){
        for (int[] ints : mazeMap) {
            for (int anInt : ints) {
                System.out.print(anInt+" ");
            }
            System.out.println();
        }
    }

    public static void printTrace(int[][] mazeMap){
        for (int[] ints : mazeMap) {
            for (int anInt : ints) {
                if(anInt == TRACE || anInt == BACK_TRACE || anInt == END_POINT){
                    System.out.print(anInt+" ");
                }else{
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }

    public static void resolveMazeIssue(int[][] mazeMap,int[] startPoint){
        //先检查是否存在终点
        checkEndPoint(mazeMap);
        //检查起点
        if(startPoint != null){
            checkStartPoint(mazeMap, startPoint);
        }
        if(startPoint == null){
            /*
            * 如果传入的起点坐标数组是null，那么使用默认的起点，
            * 默认的起点是遍历该迷宫数组时，遇到的第一个0值的坐标
            * */
            startPoint = useDefaultStartPoint(mazeMap);
            /*如果仍旧没找到默认起点，那么抛出异常*/
            if(startPoint == null){
                throw new RuntimeException("can't find a start point from this maze map!");
            }
        }
        System.out.println("start point: ("+startPoint[0]+","+startPoint[1]+")");
        searchPath(mazeMap, startPoint[0],startPoint[1]);
    }

    private static boolean searchPath(int[][] mazeMap,int rowInd,int columnInd){
        if(mazeMap[rowInd][columnInd] == END_POINT){
            System.out.println("reach end point: ("+rowInd+","+columnInd+")");
            return true;
        }else {
            if(mazeMap[rowInd][columnInd] == PATH){
                /*当前点为path点，可以通行，将其记录为trace点*/
                mazeMap[rowInd][columnInd] = TRACE;
                System.out.println("pass point: ("+rowInd+","+columnInd+")");
                /*
                 * 开始走下一步，走下一个步时会依次尝试4个方向
                 * 如果可以走通，那么继续递归走下一步，如果走不了
                 * 换下一个方向，如果4个方向都不行，那么将当前该点
                 * 记录为回溯点，不再重复走入回溯点
                 * 由此可以保证，只要迷宫有终点，那么通过递归以及回溯
                 * 一定可以走到终点，如果没有终点，那么会将整个迷宫遍历
                 * */
                if(searchPath(mazeMap, rowInd, columnInd+1)){
                    return true;
                }else if(searchPath(mazeMap, rowInd+1, columnInd)){
                    return true;
                }else if(searchPath(mazeMap, rowInd, columnInd-1)){
                    return true;
                }else if(searchPath(mazeMap, rowInd-1, columnInd)){
                    return true;
                }else {
                    /*
                     * 都走不通，那么记录当前点为回溯点并且返回false，
                     * 此时由于递归的机制，可以回到上一个调用栈，
                     * 然后最终通过不断得回调，肯定进入回到某个调用的
                     * if(mazeMap[rowInd][columnInd] == PATH)的判断中
                     * 这个时候可以回到一个新的可以走下的的点开始新的寻路
                     * */
                    mazeMap[rowInd][columnInd] = BACK_TRACE;
                    return false;
                }
            }else {
                /*
                * 当前点不是path点，那么不可以走，直接返回false
                * 回溯到下一个可以行进的点
                * */
                return false;
            }

        }
    }

    static int[] useDefaultStartPoint(int[][] mazeMap){
        int[] defaultStartPoint = new int[2];
        for (int i = 1; i < mazeMap.length; i++) {
            for (int j = 0; j < mazeMap[0].length; j++) {
                if(mazeMap[i][j] == PATH){
                    defaultStartPoint[0] = i;
                    defaultStartPoint[1] = j;
                    return defaultStartPoint;
                }
            }
        }
        return null;
    }

    static void checkStartPoint(int[][] mazeMap,int[] startPoint){
        int rowNum = mazeMap.length;
        int columnNum = mazeMap[0].length;
        int minLen = 2;
        if (startPoint == null){
            illegalStartPoint();
        }else {
            if(startPoint.length < minLen){
                illegalStartPoint();
            }else {
                /*下标越界，那么坐标点非法*/
                if(startPoint[0] >= rowNum || startPoint[1] >= columnNum){
                    illegalStartPoint();
                }
                /*下标不在迷宫边界内，那么坐标非法*/
                if(startPoint[0] == 0 || startPoint[1] == 0){
                    illegalStartPoint();
                }
            }
        }

    }

    static void illegalStartPoint(){
        throw new RuntimeException("illegal start point");
    }

    static void checkEndPoint(int[][] mazeMap){
        boolean found = false;
        for (int[] ints : mazeMap) {
            for (int anInt : ints) {
                if (anInt == END_POINT){
                    found = true;
                    break;
                }
            }
        }
        if(!found){
            throw new RuntimeException("can't find a end point from this maze map!");
        }
    }

}
