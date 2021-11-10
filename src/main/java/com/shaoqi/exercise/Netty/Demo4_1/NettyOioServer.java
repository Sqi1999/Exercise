package com.shaoqi.exercise.Netty.Demo4_1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @author shaoqi
 * @version 1.0
 * @date 2021/10/25 13:57
 */
public class NettyOioServer {

    public void Server(int port) throws Exception {
        final ByteBuf buf= Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("HI!\r\n", Charset.forName("UTF-8")));
        EventLoopGroup group=new OioEventLoopGroup();
try {
    ServerBootstrap b = new ServerBootstrap();
    b.group(group)
            .channel(OioServerSocketChannel.class)
            .localAddress(new InetSocketAddress(port))
            .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){  //添加一个ChannelInboubdHandlerAdapter以拦截和处理事件
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            //将消息写到客户端，并添加ChannelFutuerLinstener,以便消息一写完就关闭链接
                            ctx.writeAndFlush(buf.duplicate()).addListener(ChannelFutureListener.CLOSE);

                        }
                    });
                }
            });

    ChannelFuture f=b.bind().sync(); //绑定服务器接受链接
    f.channel().closeFuture().sync();
}finally {
group.shutdownGracefully().sync(); //释放所有资源
}
    }
}
