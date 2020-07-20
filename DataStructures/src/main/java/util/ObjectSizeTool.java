package util;

import java.lang.instrument.Instrumentation;

/**
 * @author Vinfer
 * @date 2020-07-20  16:04
 **/
public class ObjectSizeTool {

    private static Instrumentation instrumentation;

    public static void premain(String agentArgs, Instrumentation instP){
        instrumentation = instP;
    }

    public static long sizeOf(Object obj){
        return instrumentation.getObjectSize(obj);
    }


}
