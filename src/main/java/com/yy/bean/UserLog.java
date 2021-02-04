package com.yy.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author yuanyang
 * @version 1.0
 * @date 2021/1/26 10:36
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserLog {
    private Long id;
    private String name;
    private Integer age;
}
