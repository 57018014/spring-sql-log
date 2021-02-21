package com.yy.thread;

import java.io.IOException;

/**
 * @author yuanyang
 * @version 1.0
 * @date 2021/2/15 9:45
 */

public class ThreadTest {
    public static ThreadLocal<Person> thl = new ThreadLocal<>();
    public static ThreadLocal<Person> thl2 = new ThreadLocal<>();

    public static void main(String[] args) throws IOException {
        new Thread(()->{
            try {
                Thread.sleep(4000);
                Person person = thl.get();
                System.out.println(person);
                System.out.println("========game over====");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() ->{
            try {
                Thread.sleep(2000);
                Person person = new Person();
                person.setName("hello");
                person.setAge(1);
                thl.set(person);
                Person person1 = thl.get();
                System.out.println("set over..."+person1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
//        System.in.read();
    }

}


