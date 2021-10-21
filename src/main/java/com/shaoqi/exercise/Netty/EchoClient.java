package com.shaoqi.exercise.Netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author shaoqi
 * @version 1.0
 * @date 2021/10/20 11:15
 * 客户端主类
 */
public class EchoClient {
    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws  Exception{
        final EchoClientHandler client=new EchoClientHandler();
        EventLoopGroup group=new NioEventLoopGroup();
        try {
        Bootstrap b=new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(host,port))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(client);
                    }
                });

            ChannelFuture f = b.connect().sync();

            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }

    }

    public static void main(String[] args) throws Exception {
//        if (args.length!=2){
//            System.err.println("Usage:"+EchoClient.class.getSimpleName()+"<host><port>");
//            return;
//        }
        String host ="192.168.11.161";
        int post=8888;
        new EchoClient(host,post).start();
    }
}
