package com.shaoqi.exercise.DesignMode.ChinaOfResponsibility;

/**
 * @author shaoqi
 * @version 1.0
 * @date 2021/9/28 15:08
 * 责任链模式
 */
public class Main {
    public static void main(String[] args) {
        Msg msg=new Msg();
        msg.setMsg("大家好：)<script>,欢迎大家，大家都是996");

        //处理Msg
        String r=msg.getMsg();
        r=r.replace("<","[").replace(">","]");
        r=r.replace("996","955");
        msg.setMsg(r);
        System.out.println(msg);
    }
}

class Msg{
    String name;
    String msg;

    public String getName() {
        return name;
    }

    public String getMsg() {
        return msg;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "name='" + name + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
