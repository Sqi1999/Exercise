package com.shaoqi.exercise.Netty.NettyFingerPost.Netty.MessagePack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author shaoqi
 * @version 1.0
 * @date 2021/11/12 10:10
 * MessageToByteEncoder  吧消息转换成字节
 */
public class MsgPackEncoder extends MessageToByteEncoder<Object> {


    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {

    }
}
