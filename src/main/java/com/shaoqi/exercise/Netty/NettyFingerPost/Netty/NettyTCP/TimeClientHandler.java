package com.shaoqi.exercise.Netty.NettyFingerPost.Netty.NettyTCP;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.logging.Logger;

/**
 * @author shaoqi
 * @version 1.0
 * @date 2021/11/11 11:03
 */
@ChannelHandler.Sharable
public class TimeClientHandler extends ChannelInboundHandlerAdapter {
    private static  final Logger logger=Logger.getLogger(TimeClientHandler.class.getName());

    private int counter;

    private byte[]req;

    public TimeClientHandler(){
        req=("QUERY TIME ORDER"+System.getProperty("line.separator")).getBytes();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf message=null;
        for (int i = 0; i < 100; i++) {
            message=Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
       String body=(String) msg;
        System.out.println("Now is : "+body +" ; the counter is : "+ ++counter);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warning("Unexpected execption from downstream : "+
                cause.getMessage());
        ctx.close();
    }

    //会有粘包问题的正常代码
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        ByteBuf message=null;
//        for (int i = 0; i < 100; i++) {
//            message= Unpooled.buffer(req.length);
//            message.writeBytes(req);
//            ctx.writeAndFlush(message);
//        }
//    }
//
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf buf=(ByteBuf)msg;
//        byte [] req =new byte[buf.readableBytes()];
//        buf.readBytes(req);
//        String body=new String(req,"UTF-8");
//        System.out.println("Now is:"+body+";the counter is:"+ ++counter);
//    }
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        ctx.close();
//    }
}
