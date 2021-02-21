package com.yy.thread;

import lombok.Data;

/**
 * @author yuanyang
 * @version 1.0
 * @date 2021/2/15 10:22
 */
@Data
public class Person{
    private String name;
    private int age;

    public static void main(String[] args) {
        Person person = new Person();
        System.out.println("-----");
    }
}
