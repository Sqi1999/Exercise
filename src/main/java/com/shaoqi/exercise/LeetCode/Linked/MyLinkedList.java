package com.shaoqi.exercise.LeetCode.Linked;

import java.util.ArrayList;
import java.util.Stack;

/**
 * @author shaoqi
 * @version 1.0
 * @date 2021/10/9 14:26
 */
public class MyLinkedList {
//["MyLinkedList","addAtHead","deleteAtIndex","addAtHead","addAtHead","addAtHead","addAtHead","addAtHead","addAtTail","get","deleteAtIndex","deleteAtIndex"]
//        [[],[2],[1],[2],[7],[3],[2],[5],[5],[5],[6],[4]]
    public static void main(String[] args) {
        MyLinkedList linkedList = new MyLinkedList();
        linkedList.addAtHead(2);
        System.out.println(linkedList);
//        linkedList.deleteAtIndex(1);
//        linkedList.addAtIndex(1,2);   //链表变为1-> 2-> 3
//         linkedList.get(1);//返回2
//        linkedList.deleteAtIndex(1);  //现在链表是1-> 3
//        linkedList.get(1);

        Node a= linkedList.head;
        while (a!=null){
            System.out.print(a.val+"  ");
            a=a.next;
        }
    }

    class Node{
        public int val;
        public Node next;
        public  Node(int x){
            this.val=x;
        }
    }
    int size;
    Node head;

    public MyLinkedList() {
        this.size=0;
        this.head=null;
    }

    public int get(int index) {
        if (index<0||index>=size||head==null)
        {
         return -1;
        }
        Node tmp=this.head;
        for (int i=0;i<index;i++){
            tmp=tmp.next;
        }
        return tmp.val;
    }



    public void addAtHead(int val) {
        Node node=new Node(val);
        node.next=this.head;
        this.head=node;
        size++;

    }

    public void addAtTail(int val) {
        if (size==0){
            this.head=new Node(val);
            head.next=null;
            size++;
        }else{
            Node tmp=this.head;
            while (tmp.next!=null){
                tmp=tmp.next;
            }
            Node tail=new Node(val);
            tail.next=null;
            tmp.next=tail;
            size++;
        }
    }

    public void addAtIndex(int index, int val) {
        if (index>this.size){return;}
        if (index<=0){addAtHead(val);return;}
        if (index==this.size){addAtTail(val);return;}
        Node tmp=this.head;
        for (int i=0;i<index-1;i++){
            tmp=tmp.next;
        }
//        System.out.println(tmp.val);
        Node tail=new Node(val);
        tail.next=tmp.next;
        tmp.next=tail;
        size++;
    }

    public void deleteAtIndex(int index) {
        if (index>=this.size){return;}
        if (index==0){
           if (size!=1){
               Node tmp=this.head.next;
               this.head.next=null;
               this.head=tmp;
               size--;
               return;
           }else{
               this.head=null;size--;return;
           }
        }
        Node tmp=this.head;
        for (int i=0;i<index-1;i++){
            tmp=tmp.next;
        }
//        System.out.println(tmp.val);
//        Node n=tmp.next;
//        tmp.next=n;
        tmp.next=tmp.next.next;
        size--;

    }

    public int[] reversePrint(Node head) {
        Stack<Integer> stack=new Stack();

        while (head!=null){
            stack.push(head.val);
            head=head.next;
        }
        int [] a=new int[stack.size()];
        int i=0;
        while (!stack.empty()){
            a[i]=stack.pop();
            i++;
        }
        return a;
    }


}
