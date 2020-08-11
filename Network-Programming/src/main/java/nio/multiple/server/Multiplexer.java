package nio.multiple.server;

import nio.multiple.server.async.AsyncReadHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 多路复用器
 * @author Vinfer
 * @date 2020-08-11  09:05
 **/
public class Multiplexer implements Runnable{

    /** 事件选择器/轮询器，多路复用器的核心*/
    private Selector selector;
    /** stop必须使用volatile修饰，保证可见性*/
    private volatile boolean stop = false;
    private static final int DEFAULT_CORE_SIZE = Runtime.getRuntime().availableProcessors() * 2;
    private ThreadPoolExecutor executor;
    /** 是否使用异步处理器处理读事件，默认为false*/
    private boolean useAsyncHandler = false;

    /**
     * 允许外部手动控制服务端的停止与关闭
     */
    public void stop(){
        this.stop = true;
    }

    public void useAsyncHandle(){
        if(!useAsyncHandler){
            useAsyncHandler = true;
            executor = new ThreadPoolExecutor(DEFAULT_CORE_SIZE,DEFAULT_CORE_SIZE,
                    0L,TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100),
                    new ThreadPoolExecutor.DiscardOldestPolicy());
        }
    }

    public Multiplexer(int port){
        try {
            //打开channel，此时可以开始接收客户端连接
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            //拿到一个事件选择器
            selector = Selector.open();
            //选择器模式下，将通道设置为非阻塞
            serverChannel.configureBlocking(false);
            //绑定端口并且设置等待队列长度
            serverChannel.socket().bind(new InetSocketAddress(port),1024);
            System.out.println("server start...");
            /*
            * 最开始的事件为连接接收事件，这里进行注册
            * 注册之后selector开始监听连接事件
            * */
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (Exception e) {
            e.printStackTrace();
            //如果初始化阶段出现异常，直接退出程序
            System.exit(1);
        }
    }


    @Override
    public void run() {
        /*
        * 外层循环是为了保持线程的执行，不让线程接收
        * 可以让服务端一直监听事件
        * */
        while (!stop){
            try {
                /*
                * 这里阻塞，进行通道中的事件轮询
                * */
                int clientConnectedNum = selector.select();
                if(clientConnectedNum == 0){
                    //没有客户端进行连接时，进行自旋
                    continue;
                }
                /*
                * 当有客户端进行连接后，遍历获取到的selectionKey
                * */
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey key = null;
                while (it.hasNext()){
                    key = it.next();
                    //遍历一个后立刻移除
                    it.remove();
                    try {
                        eventHandler(key);
                    }catch (Exception e){
                        if(key != null){
                            /*
                            * 如果出现异常，那么取消本次事件的处理
                            * 并且将该客户端连接关闭
                            * 要想在这里捕获到客户端强制关闭连接的异常
                            * 必须让下层调用的方法往外抛出异常，即给方法签名加异常
                            * 不要在下层方法中做try-catch
                            * */
                            key.cancel();
                            if(key.channel() != null){
                                key.channel().close();
                            }
                        }
                    }

                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        /*
        * 手动触发停止后，关闭selector
        * */
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void eventHandler(SelectionKey key) throws IOException {
        //判断key的合法性再做进一步处理
        if(key.isValid()){
            /*
            * 通过判断key的类型，具体时间交由具体的时间处理器处理
            * 由于服务端的写是由服务端主动发起的，因此不需要判断写的类型
            * */
           if(key.isAcceptable()){
               handleAccept(key);
           }
           if (key.isReadable()){
               //如果涉及到比较复杂耗时较长的业务处理，也可以使用异步处理器
               if(useAsyncHandler){
                   executor.execute(new AsyncReadHandler(key));
               }else{
                   handleRead(key);
               }
           }
        }
    }

    private void handleAccept(SelectionKey key){
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        //在这里进行三次握手，建立真正的tcp连接
        try {
            SocketChannel clientChannel = serverSocketChannel.accept();
            System.out.println("connection establish，client "+clientChannel.getRemoteAddress().toString());
            //同样也将客户端的channel设置为非阻塞
            clientChannel.configureBlocking(false);
            //连接建立后，为该通道注册读事件，开始关注读事件
            clientChannel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (serverSocketChannel != null){
                try {
                    serverSocketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void handleRead(SelectionKey key) throws IOException {
        /*
        * 初始化读取缓冲区，分配1024byte的堆内内存
        * ByteBuffer.allocateDirect()是为缓存区分配直接内存，
        * 直接内存属于堆外的内核态内存，可以实现零拷贝
        * 但如果非大容量读取或者非频繁操作、生命周期长的对象，不建议使用直接内存
        * 使用直接内存建议手动直接释放
        * */
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        SocketChannel clientChannel = (SocketChannel) key.channel();
        int len = clientChannel.read(readBuffer);
        if(len>0){
            /*
             * 读写模式反转，该操作是必须的，将limit=position，position=0
             * Buffer对象的底层是一个数组，并且Buffer对象对该数组
             * 设置有两个关键指针：position：当前指针，指向最后一个元素 和limit：指向数组尾部
             * 在进行字节填充/读取时，position不断后移，而limit不动
             * 那么当需要拿到实际的字节数据，也就意味着需要从0读到position处
             * 因此需要做该反转操作（因为读写的算法的设定，该操作必须要有）
             * 所以下面的申请的字节数组的大小也就是Buffer.remaining = limit - position
             * */
            readBuffer.flip();
            //初始化字节数组
            byte[] data = new byte[readBuffer.remaining()];
            //将读取到的内存填充到字节数组中
            readBuffer.get(data);
            String body = new String(data, StandardCharsets.UTF_8);
            System.out.println("read data："+body);
            handleWrite(clientChannel, body);
        }
    }

    private void handleWrite(SocketChannel sc,String msg) throws IOException {
        if(msg != null && msg.length()>0){
            String response = "data has been received："+msg;
            byte[] resBytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(resBytes.length);
            writeBuffer.put(resBytes);
            //写前同样需要进行一次读写反转
            writeBuffer.flip();
            sc.write(writeBuffer);
            System.out.println("response end");
        }
    }


}
