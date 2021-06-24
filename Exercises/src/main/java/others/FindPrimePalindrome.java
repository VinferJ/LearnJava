package others;



/**
 * 索信达科技2021应届生春招笔试题：寻找范围内的质数回文数
 * 数据输入范围：0~1e，要求时间在10s之内，内存不大于128M
 *
 * @author vinfer
 * @date 2021-03-10 23:01
 */
public class FindPrimePalindrome {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        doSearch(0, 100000000);
        long end = System.currentTimeMillis();
        System.out.println("total: " + (end - start) + "ms");
    }

    public static void doSearch(int x,int y){
        for (int i = x; i <= y; i++) {
            if (isPrime(i) && isPalindrome(i)){
                System.out.println(i);
            }
        }
    }

    public static boolean isPrime(int num){
        // 质数的定义：只含有1和自身两个因数的自然数称为质数
        if (num % 2 == 0 || num == 1){
            return false;
        }else {
            // 计算整除计算到评分跟即可
            int sqrt = (int) Math.sqrt(num);
            for (int i = 2; i <= sqrt; i++) {
                if (num % i == 0){
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isPalindrome(int num) {
        if(num < 0){
            return false;
        }
        int temp = num;
        int x=0,target=0;
        // 将数位从右向左取出，然后重新计算和
        // 如果新的和的值与原始数相等，那么就是回文
        // 例如：121 依次取出为：1 2 1
        // 计算和：
        //  第一次：t = 0 * 10 + 1 = 1
        //  第二次：t = 1 * 10 + 2 = 12
        //  第三次：t = 12 * 10 + 1 = 121
        // 如果不是回文，会因为数位高低变化而导致和计算前后不相等
        while(temp != 0){
            x = temp % 10;
            target = target * 10 + x;
            temp = temp / 10;
        }
        return num == target;
    }

}
