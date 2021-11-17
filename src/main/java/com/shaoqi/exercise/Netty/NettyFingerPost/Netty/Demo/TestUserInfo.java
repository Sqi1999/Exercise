package com.shaoqi.exercise.Netty.NettyFingerPost.Netty.Demo;

import org.apache.catalina.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

/**
 * @author shaoqi
 * @version 1.0
 * @date 2021/11/12 9:45
 */
public class TestUserInfo {

    //大小测试  JDK序列化机制编码后的二进制数组大小是二进制编码的5.29倍
//    public static void main(String[] args) throws IOException {
//        UserInfo info=new UserInfo();
//        info.bulidUserID(100).buildUserName("Welcome to Netty");
//        ByteArrayOutputStream bos=new ByteArrayOutputStream();
//        ObjectOutputStream os=new ObjectOutputStream(bos);
//        os.writeObject(info);
//        os.flush();
//        os.close();
//        byte[]b=bos.toByteArray();
//        System.out.println("The jdk serilizable length is : "+b.length);
//        bos.close();
//        System.out.println("-----------------------------------");
//        System.out.println("The byte array serializable length is : "+info.codeC().length);
//    }

    //性能测试，编码一百万次java序列化的性能只有二进制编码的6.17%左右，可见java原生序列化的性能太差
    public static void main(String[] args)throws IOException {
        UserInfo info=new UserInfo();
        info.bulidUserID(100).buildUserName("Welcome to Nettty");
        int loop=1000000;
        ByteArrayOutputStream bos=null;
        ObjectOutputStream os=null;
        long startTime=System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            bos=new ByteArrayOutputStream();
            os=new ObjectOutputStream(bos);
            os.writeObject(info);
            os.flush();
            os.close();
            byte[]b=bos.toByteArray();
            bos.close();
        }
        long endTiem=System.currentTimeMillis();
        System.out.println("The jdk serializable cost time is : " +(endTiem-startTime)+"ms");
        System.out.println("-----------------------------------");
        ByteBuffer buffer=ByteBuffer.allocate(1024);
        startTime=System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            byte[]b=info.codeD(buffer);
        }
        endTiem =System.currentTimeMillis();
        System.out.println("The byte arrat serializable cost time is :"+(endTiem-startTime)+"ms");
    }


}
