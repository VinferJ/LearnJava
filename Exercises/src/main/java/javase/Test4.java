package javase;

/**
 * @author vinfer
 * @date 2021-03-10 20:45
 */
public class Test4 {

    public static void main(String[] args) {
        System.out.println(test());
        System.out.println( -12 % -5);
    }

    static int test(){
        int temp = 1;
        try {
            System.out.println(temp);
            // ++操作后temp的值已经被修改为2
            // 由于有finally语句，所以在return之前会执行finally语句块
            // 此时temp的值会被缓存在return的数据域中
            // 执行finally中++操作，temp=3，输出temp
            // 回到return语句，此时将缓存空间的的值返回，缓存空间的temp的值是2、
            // 如果finally中也有return语句，那么会刷新try或catch中的return的缓存空间
            // 因此输出顺序应该为：1 3 2
            return ++temp;
        }catch (Exception e){
            System.out.println(temp);
            return ++temp;
        }finally {
            ++temp;
            System.out.println(temp);
        }
    }

}
