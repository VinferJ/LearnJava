package nio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.SocketHandler;

/**
 * @author by 江文发
 * @date 2020-08-03    03:04
 **/
public class ServerThread implements Runnable{

    private static ConcurrentHashMap<String, Socket> socketMap = new ConcurrentHashMap<String, Socket>();

    public ServerThread(Socket socket){
        socketMap.put(UUID.randomUUID().toString(), socket);
    }

    public void run() {
        int len = 0;
        InputStream in = null;
        OutputStream out = null;
        Socket accept = null;
        byte[] buffer = new byte[1024];
        while (true){
            for (Socket socket:socketMap.values()){
                try {
                    accept = socket;
                    in = accept.getInputStream();
                    out = accept.getOutputStream();
                    while ((len= in.read(buffer))>0){
                        System.out.println(new String(buffer,0,len));
                        out.write("read success".getBytes());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
