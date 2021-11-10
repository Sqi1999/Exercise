package com.shaoqi.exercise.Netty.NettyFingerPost.BIO;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author shaoqi
 * @version 1.0
 * @date 2021/11/8 9:53
 */
public class TimeServer {
    public static void main(String[] args) throws IOException {
        int port=8080;
        if (args!=null&args.length>0){
            port=Integer.valueOf(args[0]);
        }
        ServerSocket server=null;

        try {
            server=new ServerSocket(port);
            System.out.println("The Time server is start in port:"+port);
            Socket socket=null;
            while(true){
                socket=server.accept();
                new Thread(new TimeServerHandler(socket)).start();
            }

        } finally {
            if (server!=null){
                System.out.println();
                server.close();
                server=null;
            }
        }

    }
}
