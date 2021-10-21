package com.shaoqi.exercise.IO;

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
                selector.select();
            }catch (IOException e){
                e.printStackTrace();
                break;
            }


            Set<SelectionKey> readyKey=selector.selectedKeys();
            Iterator<SelectionKey> iterator=readyKey.iterator();
            while (iterator.hasNext()){
                SelectionKey key=iterator.next();
                iterator.remove();
                try{
                    if (key.isAcceptable()){
                        ServerSocketChannel server=(ServerSocketChannel)key.channel();

                        SocketChannel client=server.accept();
                        client.configureBlocking(false);
                        client.register(selector,SelectionKey.OP_WRITE|SelectionKey.OP_READ,msg.duplicate());

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
