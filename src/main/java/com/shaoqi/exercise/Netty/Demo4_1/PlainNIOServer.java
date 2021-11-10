package com.shaoqi.exercise.Netty.Demo4_1;

import org.springframework.expression.spel.ast.Selection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author shaoqi
 * @version 1.0
 * @date 2021/10/21 14:31
 * 未使用nentty的异步网络编程
 */
public class PlainNIOServer {
    public void server(int port)throws IOException {
        ServerSocketChannel serverChannel=ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        ServerSocket ssocket=serverChannel.socket();
        InetSocketAddress address=new InetSocketAddress(port);
        ssocket.bind(address);

        Selector selector=Selector.open();  //打开Selector处理Channel
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        final ByteBuffer msg=ByteBuffer.wrap("HI!\r\n".getBytes());
        while (true){
            try{
                selector.select();  //需要等待处理的新事件，阻塞到一直持续到下一个事件的传入
            }catch (IOException e){
                e.printStackTrace();
                break;
            }


            Set<SelectionKey> readyKey=selector.selectedKeys();  //获取所有的SleectedKey实例
            Iterator<SelectionKey> iterator=readyKey.iterator();
            while (iterator.hasNext()){
                SelectionKey key=iterator.next();
                iterator.remove();
                try{
                    if (key.isAcceptable()){ //检查事件是不是一个新的已经就绪可以被接受的连接
                        ServerSocketChannel server=(ServerSocketChannel)key.channel();

                        SocketChannel client=server.accept();
                        client.configureBlocking(false);
                        client.register(selector,SelectionKey.OP_WRITE|SelectionKey.OP_READ,msg.duplicate());  //接收客户端并将写入选择器

                        System.out.println("Accpeted connection from"+client);
                    }
                    if (key.isWritable()){
                        SocketChannel client =(SocketChannel)key.channel();
                        ByteBuffer buffer=(ByteBuffer) key.attachment();
                        while (buffer.hasRemaining()){
                            if (client.write(buffer)==0){
                                break;
                            }
                        }
                        client.close();
                    }
                }catch (IOException e){
                    key.cancel();
                    try{
                        key.channel().close();
                    }catch (IOException ex){

                    }
                }
            }
        }
    }
}
