package bio.single;

import bio.thread.ServerThread;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * 基于BIO 模型的单线程版服务端
 * @author by Vinfer
 * @date 2020-08-03    01:09
 **/
public class BioServerSingle {

    public static void main(String[] args) {
        openConnection();
    }


    static void openConnection(){
        ServerSocket serverSocket = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            serverSocket = new ServerSocket(8081);
            while (true){
                Socket accept = serverSocket.accept();
                inputStream = accept.getInputStream();
                outputStream = accept.getOutputStream();
                outputStream.write((accept.getInetAddress().toString() + ": connection success").getBytes());
                int len = 0;
                byte[] buffer = new byte[1024];
                StringBuilder msg = new StringBuilder();
                while ((len=inputStream.read(buffer))>0){
                    msg.append(new String(buffer));
                    System.out.println("message from client :"+msg.toString());
                    outputStream.write("read success".getBytes());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(serverSocket != null){
                try {
                    serverSocket.close();
                    System.out.println("client closed");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
