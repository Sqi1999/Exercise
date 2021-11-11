package com.shaoqi.exercise.Netty.NettyFingerPost.Netty.DelimiterDecoder;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author shaoqi
 * @version 1.0
 * @date 2021/11/11 14:44
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    private int counter;

    static  final String ECHO_REQ="Hi ,Lilifef.Welcome to Netty.$_";
    public EchoClientHandler(){

    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            ctx.writeAndFlush(Unpooled.copiedBuffer(ECHO_REQ.getBytes()));
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("This is "+ ++counter +" Times receive server : {"+msg+"}");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
