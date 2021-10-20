package com.shaoqi.exercise.Netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author shaoqi
 * @version 1.0
 * @date 2021/10/20 10:07
 * 标示一个ChannelHandler可以被多个Channel安全的共享
 */
@ChannelHandler.Sharable
public class EchoServerHandler  extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        ByteBuf in=(ByteBuf) msg;
        System.out.println("Server received:"+in.toString());
        ctx.write(in);//将接受到的消息返回给发送者，而不是冲出消息
    }
    //将未决消息冲刷到远程结点，并且关闭该Channel
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)  {
        cause.printStackTrace();
        ctx.close();
    }
}
