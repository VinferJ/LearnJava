package proxy.aop.handler;

import proxy.aop.annotation.AfterRunning;
import proxy.aop.annotation.Around;
import proxy.aop.annotation.BeforeRunning;
import proxy.aop.annotation.ProxyHandler;

/**
 * @author Vinfer
 * @date 2021-01-25    12:43
 **/
@ProxyHandler(proxy = "proxy.aop.game")
public class GameProxyHandler2 {

    @BeforeRunning
    public void beforeRunning(){
        System.out.println("\nbefore running...\n");
    }

    @Around
    public Object aroundRunning(String param){
        System.out.println("\naround running...,param is["+param+"]\n");
        param = "绝地求生大逃杀";
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
