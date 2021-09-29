package com.shaoqi.exercise.IO.testTreactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author shaoqi
 * @version 1.0
 * @date 2021/9/29 11:37
 */
public class SelectorThread implements  Runnable{
    //每个线程队形一个selectoe，多线程情况下，该主机，该程序的并发客户端被分配到区多个selector上
    //注意：每个客户端只绑定了一个selector
    //其实不会有交互问题

    Selector selector=null;


    SelectorThread(){
        try {
            selector=Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

            //loop
        while(true){
            try {
                //1.select
                int num = selector.select(1); //阻塞  wakeup();
                //2.处理 selectKey
                if (num>0){
                    Set<SelectionKey> keys=selector.selectedKeys();
                    Iterator<SelectionKey> iterator = keys.iterator();
                    while(iterator.hasNext()){
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isAcceptable()){  //复杂，就是接收客户端的过程（接收之后要注册，多线程下，新的客户端注册到哪里）
                            accepHeadler(key);
                        }else if (key.isReadable()){
                            readHender(key);
                        }else if (key.isWritable()){

                        }
                    }
                }
                //3.处理一些task
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    private void accepHeadler(SelectionKey key) {
        ServerSocketChannel server = (ServerSocketChannel)key.channel();
        try {
            SocketChannel client = server.accept();
            client.configureBlocking(false);
//            client.register();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void readHender(SelectionKey key) {
        ByteBuffer buffer = (ByteBuffer) key.attachment();

        SocketChannel client= (SocketChannel)key.channel();
        buffer.clear();
        while (true){
            try {
                int num = client.read(buffer);
                if (num>0){
                    buffer.flip();  //将读到的内容直接写出
                    while (buffer.hasRemaining()){
                        client.write(buffer);
                    }
                    buffer.clear();
                }else if(num==0){
                    break;
                }else if(num<0){//客户端断开了
                    System.out.println("client :    "+client.getRemoteAddress()+"closed...");
                    key.cancel();
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
