package nio.multiple.server;

/**
 * NIO：new io，基于io多路复用模型实现
 * 服务端启动入口
 * @author Vinfer
 * @date 2020-08-04  20:24
 **/
public class MultiplexerNioServer {

    public static void main(String[] args) {
        openConnection(8080);
    }

    static void openConnection(int port){
        Multiplexer multiplexer = new Multiplexer(port);
        //使用异步的事件处理器
        //multiplexer.useAsyncHandle();
        new Thread(multiplexer,"nio-server-01").start();
    }

}
