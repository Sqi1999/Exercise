package com.shaoqi.exercise.Netty.NettyFingerPost.NIO;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author shaoqi
 * @version 1.0
 * @date 2021/11/8 15:18
 */
public class TimeClientHandle implements Runnable {
    private String host;
    private int port;
    private Selector selector;
    private SocketChannel socketChannel;
    private volatile boolean stop;

    public TimeClientHandle(String host, int port) {
        this.host = host == null ? "127.0.0.1" : host;
        this.port = port;
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false); //设置成非阻塞
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }


    @Override
    public void run() {
        try {
            doConnect();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        while (!stop){
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys=selector.selectedKeys();
                Iterator<SelectionKey> it=selectionKeys.iterator();
                SelectionKey key=null;
                while (it.hasNext()){
                    key=it.next();
                    it.remove();
                    try {
                        handleInput(key);
                    }catch (Exception  e){
                        key.cancel();
                        if (key.channel()!=null){
                            key.channel().close();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        //多路复用器关闭之后，所有注册在上面的channel和Pipe等资源都会被自动去注册关闭，所以不需要重复释放资源
        if (selector!=null){
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()){
            //判断是否链接成功
            SocketChannel sc=(SocketChannel)key.channel();
            if (key.isConnectable()){
                //如果是链接状态，说明服务端已经返回ACK应答消息
                if (sc.finishConnect()){ //对链接进行判断，如果为true表示链接成功
                    sc.register(selector,SelectionKey.OP_READ);
                    doWrite(sc);
                }else
                    System.exit(1);//链接失败，进程退出
            }
            if (key.isReadable()){
                ByteBuffer readBuffer=ByteBuffer.allocate(1024);
                int readByte=sc.read(readBuffer);
                if (readByte>0){
                    readBuffer.flip();
                    byte [] bytes=new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body=new String(bytes,"UTF-8");
                    System.out.println("Now is:"+body);
                    this.stop=true;
                }else if (readByte<0){
                    key.cancel();
                    sc.close();
                }else
                    ;
            }
        }
    }

    private void doConnect() throws IOException {
        //如果链接成功，则注册到多路复用器上，发送请求消息，应答
        if (socketChannel.connect(new InetSocketAddress(host, port))) {
            socketChannel.register(selector, SelectionKey.OP_READ);
            doWrite(socketChannel);
        } else //链接不成功，表示服务端没有返回TCP的握手标识，不代表链接失败，此时吧
        //SocketChennel注册到多路复用器上，注册SelectionKey.OP_CONNECT，当服务端返回TCP syn-ack消息后
        //Selector就能够轮询到这个SocketChannel处于链接就绪的状态
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
    }


    private void doWrite(SocketChannel sc) throws IOException {
        byte[] req = "QUEY TIMR ORDER".getBytes();
        ByteBuffer writeBuffer=ByteBuffer.allocate(req.length);
        writeBuffer.put(req);
        writeBuffer.flip();
        sc.write(writeBuffer);
        if (!writeBuffer.hasRemaining()){
            System.out.println("Send order 2 server succeed.");
        }
    }
}
