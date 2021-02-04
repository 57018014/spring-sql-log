package com.yy.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yuanyang
 * @version 1.0
 * @date 2021/1/25 9:44
 */
@Data
public class User implements Serializable {
    private Long id;
    private String name;
    private Integer age;
}
