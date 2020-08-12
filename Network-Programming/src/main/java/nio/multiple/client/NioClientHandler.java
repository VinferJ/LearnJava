package nio.multiple.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Vinfer
 * @date 2020-08-11  11:19
 **/
public class NioClientHandler implements Runnable{

    private final int PORT;
    private final String HOST_NAME;
    private SocketChannel socketChannel;
    private Selector selector;
    private volatile boolean stop = false;
    private ThreadPoolExecutor executor;

    public NioClientHandler(int port,String hostName){
       this.PORT = port;
       this.HOST_NAME = hostName;
       try {
           selector = Selector.open();
           socketChannel = SocketChannel.open();
           //选择器模式需要非阻塞通道
           socketChannel.configureBlocking(false);
           //初始化线程池，用于消息读取
           executor = new ThreadPoolExecutor(5,5, 0L,
                   TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(20),
                   new ThreadPoolExecutor.DiscardOldestPolicy());
       }catch (Exception e){
           e.printStackTrace();
           System.exit(1);
       }
    }

    public void stop(){
        this.stop = true;
    }

    @Override
    public void run() {
        try {
            doConnect();
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
        while (!stop){
            try {
                int activeEventNum = selector.select(1000);
                if(activeEventNum == 0){
                    //没有活跃事件时进行自旋
                    continue;
                }
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey key = null;
                while (it.hasNext()){
                    key = it.next();
                    it.remove();
                    try {
                        eventHandler(key);
                    }catch (Exception e){
                        /*
                        * 触发异常手动取消事件并关闭该客户端的通道连接
                        * */
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void eventHandler(SelectionKey key) throws IOException {
        if(key.isValid()){
            if(key.isConnectable()){
                /*继续完成连接*/
                if(socketChannel.finishConnect()){
                    System.out.println("connection establish，server："+HOST_NAME+":"+PORT);
                    socketChannel.register(selector, SelectionKey.OP_WRITE);
                }else{
                    //如果仍旧无法连接成功，那么推出程序
                    System.exit(1);
                }
            }
            if(key.isWritable()){
                writeHandler(key);
            }
        }
    }

    private void doConnect() {
        try {
            //通道发起连接
            boolean isConnected = socketChannel.connect(new InetSocketAddress(HOST_NAME, PORT));
            if (isConnected) {
                System.out.println("connection establish，server：" + HOST_NAME + ":" + PORT);
                //建立连接后注册写事件，向服务端主动发送消息
                socketChannel.register(selector, SelectionKey.OP_WRITE);
            } else {
                //建立连接失败，那么注册事件为连接事件，让通道关注CONNECT事件,继续完成连接处理
                socketChannel.register(selector, SelectionKey.OP_CONNECT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void writeHandler(SelectionKey key) throws IOException {
        SocketChannel sc = (SocketChannel) key.channel();
        Scanner scanner = new Scanner(System.in);
        ByteBuffer writeBuffer;

        /*
         * 如果在这里阻塞进行持续向服务端发送消息
         * 那么在最后退出while循环时，会一次读取到多条服务端回应的数据
         * 每次发送数据服务端都会回应数据，这些数据会暂存在缓存区中
         * 但最后处理read时会一次全部读取，读取服务端响应应该直接在while中读
         * */
        while (true){
            System.out.println("set your option: [0-exit] [1-continue]");
            if(scanner.nextInt() == 1){
                System.out.println("input sending msg:");
                String msg = scanner.next();
                byte[] data = msg.getBytes();
                writeBuffer = ByteBuffer.allocate(data.length);
                writeBuffer.put(data);
                writeBuffer.flip();
                sc.write(writeBuffer);
            }else {
                //先关线程池再退出程序
                executor.shutdown();
                System.exit(1);
            }
            /*
            * 每次发送消息都在循环中接收对应的服务端响应
            * 由于数据的发送在当前线程已经阻塞了，所以对服务端回写消息的读取
            * 必须要异步读取，否则总是会读到上一次发送的消息
            * */
            executor.execute(new MessageReadThread(sc));
        }
    }

}
