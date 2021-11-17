package com.shaoqi.exercise.Netty.Demo_11.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 * @author shaoqi
 * @version 1.0
 * @date 2021/11/15 10:35
 * 添加SSL支持
 */
public class SslChannelInitializer extends ChannelInitializer<Channel> {

    private final SslContext context;
    private final boolean startTls;

    /**
     *
     * @param context 传入要使用的 SslContext
     * @param startTls 如果设置为true第一个写入的消息不会被加密
     */
    public SslChannelInitializer(SslContext context, boolean startTls) {
        this.context = context;
        this.startTls = startTls;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        //对于每个SslHandler实例，都使用Channel的ByteBuf-Allocator从Context取一个新的SSLEngine
        SSLEngine engine=context.newEngine(ch.alloc());
        ch.pipeline().addLast("ssl",new SslHandler(engine,startTls));
    }
}
