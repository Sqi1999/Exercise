package com.shaoqi.exercise.Netty.NettyFingerPost.NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * @author shaoqi
 * @version 1.0
 * @date 2021/11/8 13:35
 */
public class MultiplexerTimeServer implements Runnable{

    private ServerSocketChannel serverChannel;
    private Selector selector;
    private volatile boolean stop;

    /**
     * 初始化多路复用器，绑定端口
     * @param port
     */
    public MultiplexerTimeServer(int port){
        try {
            Selector.open();
            serverChannel=ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.socket().bind(new InetSocketAddress(port),1024);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("The time server is start in port:"+port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
       this.stop=true;
    }

    @Override
    public void run() {
        while (!stop){
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys=selector.selectedKeys();
                Iterator<SelectionKey>it=selectionKeys.iterator();
                SelectionKey key=null;
                while (it.hasNext()){
                    key=it.next();
                    it.remove();
                    try {
                        //处理新客户端接入
                        handleInput(key);
                    }catch (Exception e){
                        key.cancel();
                        if (key.channel()!=null){
                            key.channel().close();
                        }
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        //多路复用器关闭后，所有注册在上面的Channel和Pipe等资源会被自动去注册关闭
        //不需要重复释放资源
        if (selector!=null)
            try{
                selector.close();
            }catch (IOException e){
                e.printStackTrace();
            }
    }


    private void handleInput(SelectionKey key)throws IOException{
        if (key.isValid()){
            //处理新接入请求消息  相当于三次握手
            if (key.isAcceptable()){
                //还可以对Tcp参数进行设置，列如发送和接受缓冲区的大小
                //Add the new connection
                ServerSocketChannel ssc=(ServerSocketChannel) key.channel();
                SocketChannel sc=ssc.accept();
                sc.configureBlocking(false);
                sc.register(selector,SelectionKey.OP_READ);
            }
            //读取客户端的请求信息
            if (key.isReadable()){
                //Read the data
                SocketChannel sc=(SocketChannel) key.channel();
                ByteBuffer readBuffer=ByteBuffer.allocate(1024);
                int readByet=sc.read(readBuffer);
                //对返回的字节数来进行不同的处理 >0 =0 <0
                if (readByet>0){
                    readBuffer.flip();
                    byte [] bytes=new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body=new String(bytes,"UTF-8");
                    System.out.println("The time server receive order:"+body);
                    String currentTime="QUERY TIME ORDER".equalsIgnoreCase(body)?
                            new Date(System.currentTimeMillis()).toString()
                            :"BAD OREDR";
                    doWirte(sc,currentTime);
                }else if(readByet<0){
                    //对链路关闭
                    key.cancel();
                    sc.close();
                }else
                    ;
            }
        }
    }

    //将消息异步发给客户端
    private void doWirte(SocketChannel channel,String response)throws IOException{
        if (response!=null&&response.length()>0){
            byte [] bytes=response.getBytes();
            ByteBuffer writeBuffer=ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();//可能是读写切换
            channel.write(writeBuffer);//将接put好的数据发送出去
            /**
             * 由于是异步非阻塞的，不能保证一次吧所有需要的字节数组发送出去，此时会出现写半包的问题
             * 我们西药注册写操作，不断轮询Selector将没有发扫码和完的ByteBuffer发送完毕，
             * 可以通过ByteBuffer的hasRemain（）方法来判断消息是否发送完成，
             */
        }
    }
}
