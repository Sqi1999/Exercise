package com.shaoqi.exercise.Netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.junit.Test;
import sun.misc.InnocuousThread;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author shaoqi
 * @version 1.0
 * @date 2021/9/29 9:41
 */
public class MyNetty {


//    ByteBuf buf= PooledByteBufAllocator.DEFAULT.heapBuffer();



    @Test
    public void myBuffer(){
        ByteBuf buf= ByteBufAllocator.DEFAULT.buffer(8,20);
        print(buf);
        buf.writeBytes(new byte[]{1,2,5,4,6,8,5,4,1,2,3,5,7,4,1,4});
        print(buf);
    }

    public void print(ByteBuf bytebuf){
        System.out.println("bytebuf.isReadable():   "+bytebuf.isReadable());
        System.out.println("bytebuf.readerIndex():  "+bytebuf.readerIndex());
        System.out.println("bytebuf.readableBytes():    "+bytebuf.readableBytes());
        System.out.println("bytebuf.isWritable():   "+bytebuf.isWritable());
        System.out.println("bytebuf.writerIndex():  "+bytebuf.writerIndex());
        System.out.println("bytebuf.writableBytes():    "+bytebuf.writableBytes());
        System.out.println("bytebuf.capacity():     "+bytebuf.capacity());
        System.out.println("bytebuf.maxCapacity()   "+bytebuf.maxCapacity());
        System.out.println("bytebuf.isDirect():   "+bytebuf.isDirect());
        System.out.println("-----------------");
    }
    @Test
    public void loopExecutor() throws IOException {
        NioEventLoopGroup group=new NioEventLoopGroup(2);
        group.execute(()->{
            try{
                while(true){
                    System.out.println("11111111");
                    Thread.sleep(1000);
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        });

        group.execute(()->{
            try{
                while(true){
                    System.out.println("22222222222");
                    Thread.sleep(1000);
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        });

        System.in.read();
    }

    @Test
    public void clientMde() throws Exception {
        NioEventLoopGroup thread=new NioEventLoopGroup(1);
        //客户端模式
        NioSocketChannel client=new NioSocketChannel();
        thread.register(client);

        ChannelFuture connect=client.connect(new InetSocketAddress("192.168.11.161",9090));
        ChannelFuture sync=connect.sync();

        ByteBuf buf= Unpooled.copiedBuffer("Hello server".getBytes());
        ChannelFuture send=client.writeAndFlush(buf);
        send.sync();



        sync.channel().closeFuture().sync();
        System.out.println("client over");
    }
}



