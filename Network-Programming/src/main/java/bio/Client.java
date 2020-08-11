package bio;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * BIO客户端
 * @author by 江文发
 * @date 2020-08-03    02:22
 **/
public class Client {


    public static void main(String[] args) {
        connectToServer();
    }

    static void connectToServer(){
        Socket clientSocket = null;
        InputStream in = null;
        OutputStream out = null;
        Scanner scanner = new Scanner(System.in);
        try {
            while (true){
                clientSocket = new Socket("localhost",8080);
                in = clientSocket.getInputStream();
                out = clientSocket.getOutputStream();
                byte[] buffer = new byte[1024];
                int len = in.read(buffer);
                if(len>0){
                    System.out.println(new String(buffer,0,len));
                }
                int option;
                String msg = "";
                while (true){
                    System.out.println("your option: [1-send] [0-exit]");
                    option = scanner.nextInt();
                    if(option==1){
                        msg = scanner.next();
                    }else {
                        clientSocket.close();
                        break;
                    }
                    out.write(msg.getBytes());
                    len = in.read(buffer);
                    System.out.println(new String(buffer,0, len));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
