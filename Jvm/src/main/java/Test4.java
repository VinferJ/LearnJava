/**
 * @author vinfer
 * @date 2021-03-02 21:27
 */
public class Test4 {

    public static void main(String[] args) {
        gcTest3();
    }

    static void gcTest1(){
        byte[] placeholder = new byte[64 * 1024 * 1024];
        System.gc();
    }

    static void gcTest2(){
        {
            byte[] placeholder = new byte[64 * 1024 * 1024];
        }
        System.gc();
    }

    static void gcTest3(){
        {
            byte[] placeholder = new byte[64 * 1024 * 1024];
        }
        int a = 0;
        System.gc();
    }

}
