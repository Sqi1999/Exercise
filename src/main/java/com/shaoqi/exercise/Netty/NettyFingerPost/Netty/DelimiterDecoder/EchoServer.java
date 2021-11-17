package com.shaoqi.exercise.Netty.NettyFingerPost.Netty.DelimiterDecoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author shaoqi
 * @version 1.0
 * @date 2021/11/11 14:06
 */
public class EchoServer {

    public void bind(int port)throws Exception{
        EventLoopGroup bossgroup=new NioEventLoopGroup();
        EventLoopGroup workerGroup=new NioEventLoopGroup();

        try{
            ServerBootstrap b=new ServerBootstrap();
            b.group(bossgroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,100)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            /**
                             * 首先创建缓冲对象，创建Delimiter对象，将其加入到cahnnelPipeline中
                             * 1024表示单条消息最大长度，当到达长度后还没找到分隔符，就抛出异常，防止由于异常码流失
                             * 分隔符导致的内存溢出，这是netty解码器的可靠性保护，第二个就是分隔符缓冲对象
                             */
                            ByteBuf delimiter= Unpooled.copiedBuffer("head_data".getBytes());
                            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,delimiter));
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new EchoServerHandler());
                        }
                    });
            ChannelFuture f=b.bind(port).sync();
            f.channel().closeFuture().sync();
        }finally {
            bossgroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port=8080;
        new EchoServer().bind(port);
    }
}
