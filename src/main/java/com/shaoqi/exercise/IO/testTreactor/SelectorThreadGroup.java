package com.shaoqi.exercise.IO.testTreactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author shaoqi
 * @version 1.0
 * @date 2021/9/29 13:58
 */
public class SelectorThreadGroup {

    SelectorThread[] sts;
    ServerSocketChannel server;

    AtomicInteger xid=new AtomicInteger(0);


    SelectorThreadGroup(int number){
        //number  线程数
        sts=new SelectorThread[number];
        for (int i = 0; i < number; i++) {
            sts[i]=new SelectorThread(this);
            new Thread(sts[i]).start();
        }
    }

    public void bind(int i) {
        try {
            server=ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(i));

            //注册到那个selector
            nextSelector(server);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void  nextSelector(Channel c){
        SelectorThread st = next();
        //添加到队列
        st.lqp.add(c);
        //不阻塞
        st.selector.wakeup();
//
//        try {
//            //重点  c可能是selver  有可能是client
//            ServerSocketChannel s = (ServerSocketChannel) c;
//            //会阻塞
//            st.selector.wakeup();  //让selector的select 立刻返回，不阻塞
//            s.register(st.selector, SelectionKey.OP_ACCEPT);//要呼应上  int num=selector.slect
//        } catch (ClosedChannelException e) {
//            e.printStackTrace();
//        }
    }
    //无论serversocket socket 都要复用这个方法
    private SelectorThread next() {
        int index = xid.incrementAndGet() % sts.length;
        return sts[index];
    }


}
