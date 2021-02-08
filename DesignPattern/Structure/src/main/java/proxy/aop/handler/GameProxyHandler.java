package proxy.aop.handler;

import proxy.aop.annotation.*;

/**
 * @author Vinfer
 * @date 2021-01-25    12:43
 **/
@ProxyHandler(proxy = "proxy.aop.test")
public class GameProxyHandler {

    @BeforeRunning
    public void beforeRunning(){
        System.out.println("\nbefore running...\n");
    }

    @Around
    public Object aroundRunning(String param){
        System.out.println("\naround running...,param is["+param+"]\n");
        param = "dnf";
        return param;
    }

    @AfterRunning
    public void afterRunning(){
        System.out.println("\nafter running...\n");
    }

    @Override
    public String toString() {
        return "";
    }
}
