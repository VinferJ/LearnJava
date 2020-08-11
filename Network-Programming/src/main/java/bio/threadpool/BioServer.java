package bio.threadpool;

import bio.thread.ServerThread;
import org.omg.SendingContext.RunTime;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池改造BIO
 * @author Vinfer
 * @date 2020-08-11  08:50
 **/
public class BioServer {

    private static final int DEFAULT_CORE_SIZE = Runtime.getRuntime().availableProcessors() * 2;

    public static void main(String[] args) {
        openConnection(8080);
    }


    static void openConnection(int port){
        ServerSocket serverSocket = null;
        //手动创建线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                DEFAULT_CORE_SIZE, DEFAULT_CORE_SIZE, 0L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(100),new ThreadPoolExecutor.DiscardOldestPolicy());
        try {
            serverSocket = new ServerSocket();
            executor.execute(new ServerThread(serverSocket.accept()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
