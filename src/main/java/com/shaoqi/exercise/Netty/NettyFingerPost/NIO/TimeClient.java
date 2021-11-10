package com.shaoqi.exercise.Netty.NettyFingerPost.NIO;

/**
 * @author shaoqi
 * @version 1.0
 * @date 2021/11/8 15:14
 */
public class TimeClient {

    public static void main(String[] args) {
          int port=8080;
        if (args!=null&&args.length>0){
            try {
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e){
                //
            }
        }

        new  Thread(new TimeClientHandle("127.0.0.1",port)).start();
    }
}
