package lsp;

import java.util.Map;

/**
 * @author Vinfer
 * @version v1.1
 * @date 2020-10-30    15:55
 * @description
 **/
public class Son extends Father{

    public void doSomething(Map<String, Object> map) {
        System.out.println("子类执行了doSomething...");
    }

}
