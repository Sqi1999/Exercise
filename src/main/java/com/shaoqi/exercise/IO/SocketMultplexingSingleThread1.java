package com.shaoqi.exercise.IO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 单线程多路复用器
 */
public class SocketMultplexingSingleThread1 {
    private ServerSocketChannel server=null;
    private Selector selector=null;    //等于linux的多路复用器
    int port=9090;

    public void initServer(){
        try {
            server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(port));
            /**
             * 在EPOLL模型下 open->epoll_create->fd3
             * select，poll   epoll  优先选择epoll
             */
            selector=Selector.open();
            /**
             * server  约等于listen的状态  fd4
             * 如果在select，poll模型下：jvm会开辟一个数组，吧fd4放进去
             * epoll：epoll_ctl（fd3，add.fd4,EPOLLIN）
             */
            server.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        initServer();
        System.out.println("服务器启动了");
        try{
            while (true){
                //调用多路复用器
                Set<SelectionKey> keys=selector.keys();
                System.out.println("size :"+keys.size());
                /**
                 *epoll_wait
                 * select() 是啥意思
                 * select poll 其实内核的select（fd4） pull (fd4)
                 * epoll 其实是内核的epoll_wait
                 * 参数可以设置时间，如果没有时间 ->阻塞  有时间->设置一个超时时间
                 *
                 * 或者用selector.wakeuo() 控制不阻塞  返回去o
                 */
                while (selector.select(500)>0){
                    //返回有状态的fd集合
                    Set<SelectionKey> selectionKeys=selector.selectedKeys();
                    /**
                     * 不管什么多路复用器，只能给一个状态，我还要一个一个去处理他们的R/W
                     * 回忆  NIO对每一个fd的调用 浪费资源 这里调用了一次select的方法 具体就可以知道有哪些R/W
                     * socket 有两种返回   listen  通信的R/W
                     */
                    Iterator<SelectionKey> iter=selectionKeys.iterator();
                    while (iter.hasNext()){
                        SelectionKey key= iter.next();
                        iter.remove();  //不移除会重复过滤
                        if (key.isAcceptable()){  //listen
                            /**
                             * 看代码的时候，这里是重点，如果去接收一个新的连接
                             * 语义上 accept 接收新的并返回新的FD对象
                             * 新的FD怎么办
                             *
                             * select poll 因为他们内核没有空间，那么JVM中保存的和前面那个listen的一起
                             * epoll:我们希望epoll_ctl吧新的客户端fd注册进去
                             */
                            acceptHandle(key);
                        }else if (key.isReadable()){//R/W
                            readHandle(key);
                        }
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void readHandle(SelectionKey key) {
        try{
            ServerSocketChannel ssc=(ServerSocketChannel) key.channel();
            SocketChannel client=ssc.accept();
            client.configureBlocking(false);
            ByteBuffer buffer=ByteBuffer.allocate(8192);
            client.register(selector,SelectionKey.OP_ACCEPT,buffer);
            System.out.println("----------------------");
            System.out.println("新的客户端连接："+client.getLocalAddress());
            System.out.println("----------------------");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void acceptHandle(SelectionKey key) {
    }
}
