package nio.multiple.server.async;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author Vinfer
 * @date 2020-08-11  10:22
 **/
public class AsyncReadHandler implements Runnable{

    private final SelectionKey KEY;

    public AsyncReadHandler(SelectionKey key){
        this.KEY = key;
    }

    @Override
    public void run() {
        try {
            read(KEY);
        }catch (Exception e){
            if(KEY != null){
                KEY.cancel();
                if(KEY.channel() != null){
                    try {
                        KEY.channel().close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        }

    }

    private void read(SelectionKey key) throws IOException {
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        SocketChannel clientChannel = (SocketChannel) key.channel();
        int len = clientChannel.read(readBuffer);
        if(len>0) {
            readBuffer.flip();
            byte[] data = new byte[readBuffer.remaining()];
            readBuffer.get(data);
            String body = new String(data, StandardCharsets.UTF_8);
            System.out.println("read data：" + body);
            response(clientChannel, body);
        }
    }

    private void response(SocketChannel sc,String msg) throws IOException {
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
