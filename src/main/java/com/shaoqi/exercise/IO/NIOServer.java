package com.shaoqi.exercise.IO;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;

/**
 * 客户端
 */
public class NIOServer {

    public static void main(String[] args) throws Exception{

        LinkedList<SocketChannel> clients=new LinkedList<>();

        ServerSocketChannel ss=ServerSocketChannel.open();
        ss.bind(new InetSocketAddress(9090));
        ss.configureBlocking(false);  // 设置非阻塞 重点  OS  NONBLOCKING   监听不要阻塞  true就是BIO  false就是NIO


//        ss.setOption(StandardSocketOptions.TCP_NODELAY,false);


        while (true){
            Thread.sleep(1000);
            SocketChannel client=ss.accept();  //会不会阻塞  BIOaccept会导致阻塞 -1 NULL

            if (client==null){
                System.out.println("null......");
            }else{
                client.configureBlocking(false); //重点
                int port=client.socket().getPort();
                System.out.println("客户端连接："+port);
                clients.add(client);
            }
            ByteBuffer buffer=ByteBuffer.allocateDirect(4096);  //内存分配可以在堆里，也可以在堆外
            for (SocketChannel c:clients){  //串行化  多线程！！！
                int num=c.read(buffer); //>0 -1 0 //不会阻塞
                if(num>0){
                    buffer.flip();
                    byte[]aaa=new byte[buffer.limit()];
                    buffer.get(aaa);

                    String b=new String(aaa);

                    System.out.println(c.socket().getPort()+":"+b);
                    buffer.clear();
                }
            }
        }

    }

}
