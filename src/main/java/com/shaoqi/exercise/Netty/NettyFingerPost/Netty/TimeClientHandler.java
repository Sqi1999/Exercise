package com.shaoqi.exercise.Netty.NettyFingerPost.Netty;

import com.shaoqi.exercise.Netty.NettyFingerPost.NIO.TimeClientHandle;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.logging.Logger;

/**
 * @author shaoqi
 * @version 1.0
 * @date 2021/11/10 15:37
 */
public class TimeClientHandler  extends ChannelInboundHandlerAdapter {
   private static final Logger logger=Logger.getLogger(TimeClientHandle.class.getName());
   private final ByteBuf firstMessage;

    public TimeClientHandler() {
        byte[] req="QUERY TIME ORDER".getBytes();
        firstMessage= Unpooled.buffer(req.length);
        firstMessage.writeBytes(req);
    }

    /**
     * 客户端和服务端的TCP链路建立链接成功直呼
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client channelActive");
       ctx.writeAndFlush(firstMessage);
    }

    /**
     * 当服务端返回应答消息时
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf=(ByteBuf)msg;
        byte []req=new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body=new String(req,"UTF-8");
        System.out.println("Now is:"+body);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warning("Unexpected execption from downstream :"+cause.getMessage());
        ctx.close();
    }
}
