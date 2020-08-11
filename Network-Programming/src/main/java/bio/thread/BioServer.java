package bio.thread;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 多线程改造BIO
 * @author Vinfer
 * @date 2020-08-11  08:46
 **/
public class BioServer {


    public static void main(String[] args) {
        openConnection(8080);
    }

    static void openConnection(int port){
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            new Thread(new ServerThread(serverSocket.accept()));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (serverSocket != null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
