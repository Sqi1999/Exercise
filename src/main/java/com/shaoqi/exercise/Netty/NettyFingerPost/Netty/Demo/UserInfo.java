package com.shaoqi.exercise.Netty.NettyFingerPost.Netty.Demo;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * @author shaoqi
 * @version 1.0
 * @date 2021/11/12 9:37
 * 查看java序列化后字节数组大小
 */
public class UserInfo implements Serializable {

    /**
     * 默认序列号
     */
    private static  final long serialVersionUID=1L;

    private String userName;

    private int userID;

    public UserInfo buildUserName(String userName){
        this.userName=userName;
        return this;
    }

    public UserInfo bulidUserID(int userID){
        this.userID=userID;
        return this;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public byte[] codeC(){
        ByteBuffer buffer=ByteBuffer.allocate(1024);
        byte[]value=this.userName.getBytes();
        buffer.putInt(value.length);
        buffer.put(value);
        buffer.putInt(this.userID);
        buffer.flip();
        value=null;
        byte []result=new byte[buffer.remaining()];
        buffer.get(result);
        return result;
    }

    public byte[] codeD(ByteBuffer buffer){
        byte[]value=this.userName.getBytes();
        buffer.putInt(value.length);
        buffer.put(value);
        buffer.putInt(this.userID);
        buffer.flip();
        value=null;
        byte []result=new byte[buffer.remaining()];
        buffer.get(result);
        return result;
    }
}
