package com.shaoqi.exercise.Netty.NettyFingerPost.Netty;


import com.shaoqi.exercise.Netty.NettyFingerPost.Netty.NettyTCP.TimeServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author shaoqi
 * @version 1.0
 * @date 2021/11/10 14:56
 */
public class TimeServer {
    public void bind(int port) throws Exception {
        EventLoopGroup bossgroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossgroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChildChannelHander());
            //绑定端口，同步等待成功
            ChannelFuture f = b.bind(port).sync();
            //等待服务监听端口关闭
            f.channel().closeFuture().sync();
        } finally {
            bossgroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private class ChildChannelHander extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast((ChannelHandler) new TimeServerHandler());
        }
    }

    public static void main(String[] args) throws Exception {
        int port=8080;
        if (args!=null&&args.length>0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {

            }
        }
        new TimeServer().bind(port);
    }


}