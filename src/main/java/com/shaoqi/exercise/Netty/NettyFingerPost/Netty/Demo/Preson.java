package com.shaoqi.exercise.Netty.NettyFingerPost.Netty.Demo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.*;
import java.util.Base64;

/**
 * @author shaoqi
 * @version 1.0
 * @date 2021/11/12 11:23
 */
@ToString
@Getter
@Setter
public class Preson implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private int age;

    public Preson(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

class WriteObject {
    public static void main(String[] args) {
        String Ffflie="person.obj";
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("D://person.txt"));
            Preson preson = new Preson("邵琪好帅", 2);
            oos.writeObject(preson);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
class ReadObject {
    public static void main(String[] args) throws Exception {
        ObjectInputStream ois=new ObjectInputStream(new FileInputStream("D://person.txt"));
        Preson preson=(Preson) ois.readObject();
        System.out.println(preson);
        ois.close();
//        System.out.println(p);
    }
}
