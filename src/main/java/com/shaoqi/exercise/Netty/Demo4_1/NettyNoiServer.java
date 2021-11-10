package com.shaoqi.exercise.Netty.Demo4_1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Calendar;

/**
 * @author shaoqi
 * @version 1.0
 * @date 2021/10/25 14:10
 */
public class NettyNoiServer {

    public static void main(String[] args) {
//        ByteBuf buf=Unpooled.copiedBuffer("HI!",Charset.forName("UTF-8"));
//        if (buf.hasArray()){//检查一个BytBuf是否有数组支撑
//            byte [] array=buf.array();  //如果有，则获取对该数组的引用
//            int offset=buf.arrayOffset()+buf.readerIndex();  //计算第一个的偏移量
//            int length=buf.readableBytes(); //获取可读字节
//            //handleArray(array,offest,length)   //使用数组，偏移量，长度来调用方法
//        }else{//没有数组支撑
//            int length=buf.readableBytes(); //获取可读字节数
//            byte[] array=new byte[length];//分配一个数组来保存
//            buf.getBytes(buf.readerIndex(),array);//复制到该数组
//            //handleArray(array,0,length)  //使用数组，偏移量，长度来调用方法
//        }

//        ByteBuf buf=Unpooled.copiedBuffer("Hi!\r\n",Charset.forName("UTF-8"));
//        ByteBuf buf2=Unpooled.copiedBuffer("ShaoQi\r\n",Charset.forName("UTF-8"));
//        CompositeByteBuf cbuf=Unpooled.compositeBuffer();
//        cbuf.addComponent(true,buf);
//        cbuf.addComponent(true,buf2);
//        cbuf.removeComponent(0);
//
//        int length=cbuf.readableBytes();
//        byte [] array=new byte[length];
//        cbuf.getBytes(cbuf.readerIndex(),array);
//        handleArray(array,0,array.length)


//        Charset utf_8 = Charset.forName("UTF-8");
//        ByteBuf buf=Unpooled.copiedBuffer("Netty in Actin rocks!",utf_8);
//        ByteBuf sliced=buf.slice(0,15);
//        System.out.println(sliced.toString(utf_8));
//        buf.setByte(0,(byte)'J');   //更新索引处0字节
//        assert buf.getByte(0)==sliced.getByte(0); //将会成功，因为数据是共享的


        Charset utf_8 = Charset.forName("UTF-8");
        ByteBuf buf=Unpooled.copiedBuffer("Netty in Actin rocks!",utf_8);
        ByteBuf copy=buf.copy(0,15);
        System.out.println(copy.toString(utf_8));
        buf.setByte(0,(byte)'J');
        assert  buf.getByte(0)!=copy.getByte(0); //将会成功，因为数据不共享


    }
    public void server(int port) throws Exception {
        final ByteBuf buf= Unpooled.copiedBuffer("HI!\r\n", Charset.forName("UTF-8"));
        EventLoopGroup group=new NioEventLoopGroup();

//        EventLoopGroup group=new EpollEventLoopGroup();
        try{
            ServerBootstrap b=new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<ServerChannel>() {
                        @Override
                        protected void initChannel(ServerChannel ch) throws Exception {
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    ctx.writeAndFlush(buf.duplicate()).addListener(ChannelFutureListener.CLOSE);
                                }

                            });
                        }
                    });
            ChannelFuture f=b.bind().sync();
            f.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully().sync();
        }
    }
}
