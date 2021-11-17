package com.shaoqi.exercise.Netty.Demo_11.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;

/**
 * @author shaoqi
 * @version 1.0
 * @date 2021/11/15 10:42
 */
public class HttpPipelineInitializer extends ChannelInitializer<Channel> {

    private final boolean clientl;

    public HttpPipelineInitializer(boolean clientl) {
        this.clientl = clientl;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline cp=ch.pipeline();
        if (clientl){
            cp.addLast("decoder",new HttpResponseDecoder());
            cp.addLast("encoder",new HttpRequestEncoder());
        }else {

        }
    }
}
