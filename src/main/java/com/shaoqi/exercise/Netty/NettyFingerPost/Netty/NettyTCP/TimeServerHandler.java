package com.shaoqi.exercise.Netty.NettyFingerPost.Netty.NettyTCP;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;

import java.util.Date;

/**
 * @author shaoqi
 * @version 1.0
 * @date 2021/11/11 10:27
 * 对TimeServer的改造  模拟TCP粘包问题
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {


    private int counter;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
      String body=(String)msg;
        System.out.println("The time server receive order:"+body+"; the counter is :"+ ++counter);

        String currentTime="QUERY TIME ORDER".equalsIgnoreCase(body)?new
                Date(System.currentTimeMillis()).toString():"BAD ORDER";
        currentTime=currentTime+System.getProperty("line.separator");
        ByteBuf resp=Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.writeAndFlush(resp);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    //会有粘包问题的正常代码
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf buf=(ByteBuf) msg;
//        byte[]req=new byte[buf.readableBytes()];
//        buf.readBytes(req);
//        //服务器接受消息总数应该跟客户端大宋的消息数相同，而且请求消息删除回车换行符后应该为QUERY TIEM ORDER
//        String  body=new String(req,"UTF-8").substring(0, req.length
//        -System.getProperty("line.separator").length());
//
//        System.out.println("The time server receive order :"+body
//        +"; the counser is :"+ ++counter);
//
//        String currentTime ="QUERY TIEM ORDER".equalsIgnoreCase(body)?new
//                Date(System.currentTimeMillis()).toString():"BAD ORDER";
//        currentTime=currentTime+System.getProperty("line.separator");
//        ByteBuf resp= Unpooled.copiedBuffer(currentTime.getBytes());
//        ctx.writeAndFlush(resp);
//    }
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        ctx.close();
//    }



}
