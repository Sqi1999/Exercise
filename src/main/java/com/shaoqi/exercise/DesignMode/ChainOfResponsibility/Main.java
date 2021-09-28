package com.shaoqi.exercise.DesignMode.ChainOfResponsibility;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shaoqi
 * @version 1.0
 * @date 2021/9/28 15:08
 * 责任链模式
 */
public class Main {
    public static void main(String[] args) {
        Msg msg=new Msg();
        msg.setMsg("大家好：)<script>,欢迎大家，url大家都是996");

FilterChain fc=new FilterChain();
fc
        .add(new SensitiveFilter())
        .add(new HTMLFilter());

        FilterChain fc2=new FilterChain();
        fc2.add(new UrlFilter()).add(new UrlFilter());

        fc.add(fc2);
fc.doFilter(msg);

//        filters.add(new HTMLFilter());
//        filters.add(new SensitiveFilter());
//        for (Filter f:filters) {
//            f.doFile(msg);
//        }

        //处理Msg
//        new  HTMLFilter().doFile(msg);
//        new SensitiveFilter().doFile(msg);
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

interface  Filter{
    boolean doFilter(Msg m);
}

class HTMLFilter implements  Filter{

    @Override
    public boolean doFilter(Msg m) {
        String r=m.getMsg();
        r=r.replace("<","[").replace(">","]");

        m.setMsg(r);
        return true;
    }
}

class SensitiveFilter implements  Filter{

    @Override
    public boolean doFilter(Msg m) {
        String r=m.getMsg();
        r=r.replace("996","955");
        m.setMsg(r);
        return false;
    }
}

class FaceFilter implements  Filter{

    @Override
    public boolean doFilter(Msg m) {
        String r=m.getMsg();
        r=r.replace("：)","^V^");
        m.setMsg(r);
        return  true;
    }
}
class UrlFilter implements  Filter{

    @Override
    public boolean doFilter(Msg m) {
        String r=m.getMsg();
        r=r.replace("url","Http:121212");
        m.setMsg(r);
        return  true;
    }
}



class FilterChain implements  Filter{
    List<Filter> filters=new ArrayList<>();
    public FilterChain add(Filter f){
        filters.add(f);
        return this;
    }
    public boolean doFilter(Msg s){
        for (Filter f:filters) {
            if (!f.doFilter(s)) return false;
        }
        return true;
    }
}