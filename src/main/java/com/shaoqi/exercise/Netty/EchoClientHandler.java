package com.shaoqi.exercise.Netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author shaoqi
 * @version 1.0
 * @date 2021/10/20 11:01
 * 客户端
 * 1）链接服务器
 * 2）发送一个或者多个消息
 * 3）对于发送消息，等待并接受从服务器发回相同的消息
 * 4）关闭链接
 * SimpleChennnelInboundhandler类
 * 方法
 * channelActive（）--在存到服务器的链接已经建立之将被调用
 * channelRead0（）--当从服务器接受到一条消息时被调用
 * exceptionConfig（）--在处理过程中引发异常时候调用
 */
//@Sharable   //标记该实例可以被多个Channel共享
    @ChannelHandler.Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelActive(ChannelHandlerContext ctx)  {
        //当被通知Channle是活跃的时候，发送一条消息
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks！", CharsetUtil.UTF_8));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("Client received:"+msg.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("结束");
        cause.printStackTrace();

        ctx.close();
    }
}
