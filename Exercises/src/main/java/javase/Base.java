package javase;

/**
 * @author Vinfer
 * @date 2021-02-24    00:29
 **/
public class Base extends Thread{
    Base(){
        System.out.println("base");
    }

    float func(){
        // 大转小需要强制转换
        // 小转大不需要强制转换
        short  i = 10;
        return i;
    }

    float func2(){
        // float占4字节，long是8字节
        // 但是这里不需要转换的原因是，因为float是浮点数，真实的数位或大小仍大于8字节的long
        // 所以这里本质也属于小转大，所以不需要强制
        long i = 10L;
        return i;
    }

}
