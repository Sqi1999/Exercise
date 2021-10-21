package com.shaoqi.exercise.IO;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * @author shaoqi
 * @version 1.0
 * @date 2021/10/21 13:53
 * OIO server 未使用Netty的阻塞网络编程
 */
public class PlsninOioServer {
    public void servre(int port) throws IOException {
        final ServerSocket socket=new ServerSocket(port);

        try {
            while (true) {
                final Socket clientSocket = socket.accept();//接受链接
                System.out.println("Accepted connection from" + clientSocket);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OutputStream out;
                        try{
                            out=clientSocket.getOutputStream();
                            out.write("HI!\r\n".getBytes(Charset.forName("UTF-8"))); //将消息写给已连接的客户端
                            out.flush();
                            clientSocket.close();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                        finally {
                            try{
                                clientSocket.close();
                            }catch (IOException e){

                            }
                        }
                    }
                }).start();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
