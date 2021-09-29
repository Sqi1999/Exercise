package com.shaoqi.exercise.IO.testTreactor;

/**
 * @author shaoqi
 * @version 1.0
 * @date 2021/9/29 11:36
 */
//主线程  这不不做和IO和业务的事情
public class MianThread {

    public static void main(String[] args) {
        //1.创建IOTherad(一个或多个)
SelectorThreadGroup stg=new SelectorThreadGroup(1);
SelectorThreadGroup st1=new SelectorThreadGroup(3);//混杂模式，只有一个线程负责accept  每个都会被分配client 进行读写

        //2.应该吧监听的server 注册到某一个selector上

stg.bind(9999);
    }
}
