package nio.multiple.client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author Vinfer
 * @date 2020-08-11  20:10
 **/
public class MessageReadThread implements Runnable{

    private final SocketChannel SC;

    public MessageReadThread(SocketChannel sc){
        this.SC = sc;
    }

    @Override
    public void run() {
        readHandler();
    }

    private void readHandler(){
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        int len = 0;
        try {
            len = SC.read(readBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(len>0){
            readBuffer.flip();
            byte[] data = new byte[readBuffer.remaining()];
            readBuffer.get(data);
            String body = new String(data, StandardCharsets.UTF_8);
            System.out.println("read data："+body);
        }else {
            System.out.println("buffer is empty or reading is end");
        }
    }
}
