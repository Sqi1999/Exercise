package com.shaoqi.exercise.Netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author shaoqi
 * @version 1.0
 * @date 2021/10/20 10:15
 * 引导服务器
 */
public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
//        if (args.length!=1){
//            System.out.println("Usage:"+EchoServer.class.getSimpleName()+"<port>");
//            return;
//        }
        int port =Integer.parseInt("8888");
        new EchoServer(port).start();
    }

    public void start() throws Exception {
        final EchoServerHandler serverHandler =new EchoServerHandler();
        EventLoopGroup group=new NioEventLoopGroup();  //创建EventLoop
        try {
        ServerBootstrap b=new ServerBootstrap(); //创建ServerBootstrap
        b.group(group)
                .channel(NioServerSocketChannel.class) //指定所使用的NIO传输Channel
                .localAddress(new InetSocketAddress(port))  //设置套接字
                .childHandler(new ChannelInitializer<SocketChannel>() { //添加一个EchoServerHandler到子Channel的ChannelPipeline

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(serverHandler);  //EchoServerHandler被标注为@Shareable,所以我们可以总是使用同样的实例
                    }
                });

            ChannelFuture f=b.bind().sync();  //异步绑定服务器，调用sync方法阻塞，等待直接绑定完成
            f.channel().closeFuture().sync();//获取Channel的CloseFuture  并且阻塞当前线程直到他完成
        } finally {
            group.shutdownGracefully().sync();//关闭EventLoopGroup
        }
    }
}
