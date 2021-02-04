package com.yy;

import com.yy.bean.User;
import com.yy.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * @author yuanyang
 * @version 1.0
 * @date 2021/1/27 11:35
 */
@SpringBootTest
public class RedisTest {
    @Autowired
    UserService userService;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RedisTemplate<Object, User> userRedisTemplate;


    /**
     * String list set hash（散列） zset(有序集合）
     */
    @Test
    public void test0(){
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
        String aa = opsForValue.get("aa");
        System.out.println(aa);
    }
    @Test
    public void test1(){
        User user = userService.getUserById(1L);
        userRedisTemplate.opsForValue().set("user_id_"+user.getId(),user);
    }

}
