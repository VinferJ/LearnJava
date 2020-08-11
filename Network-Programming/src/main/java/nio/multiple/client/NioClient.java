package nio.multiple.client;

/**
 * 基于Nio实现一个客户端
 * 客户端程序入口
 * @author Vinfer
 * @date 2020-08-11  11:18
 **/
public class NioClient {

    public static void main(String[] args) {
        openConnection(8080);
    }

    static void openConnection(int port){
        NioClientHandler clientHandler = new NioClientHandler(port,"localhost");
        new Thread(clientHandler,"nio-client-01").start();
    }

}
