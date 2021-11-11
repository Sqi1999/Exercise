package com.shaoqi.exercise.Netty.NettyFingerPost.Netty.FixedDecoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author shaoqi
 * @version 1.0
 * @date 2021/11/11 15:40
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    private int counter=0;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body=(String)msg;

        System.out.println("Recive client : ["+msg+"]");
//        ctx.writeAndFlush(Unpooled.copiedBuffer(body.getBytes()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
       ctx.close();
    }
}
