package com.yy;

import com.yy.bean.User;
import com.yy.service.UserService;
import com.yy.utils.RedisGeoUtil;
import com.yy.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yuanyang
 * @version 1.0
 * @date 2021/1/27 11:35
 */
@SpringBootTest
public class RedisTest1 {

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    RedisGeoUtil redisGeoUtil;



    @Test
    public void test1() throws InterruptedException {
        String aa = redisUtil.get("aa", 0);
        System.out.println(aa);
    }
    @Test
    public void test2() throws InterruptedException {
        redisGeoUtil.geoadd("tag01",  new GeoCoordinate(126.709715,45.763368), "将军牛排道理店", 0);
        redisGeoUtil.geoadd("tag01",  new GeoCoordinate(126.709715,45.763338), "将军牛排道理店1", 0);

        redisGeoUtil.geoadd("tag01", new GeoCoordinate(126.709715,45.763468), "海底捞道理店", 0);
        redisGeoUtil.geoadd("tag01", new GeoCoordinate(126.709715,45.763538), "海底捞道理店1", 0);
    }

    /**
     * 传入经纬度，反回范围内门店的距离集合（门店信息可以保存）
     * 当前是已
     */
    @Test
    public void test3(){
        String targetName = "海底";
        GeoCoordinate coordinate = new GeoCoordinate(126.709725,45.763388);
        List<GeoRadiusResponse> list = redisGeoUtil.geoRadius("tag01", coordinate, 100.0, 0);
        System.out.println(list);
        list.forEach(item ->{
            System.out.println(new String(item.getMember())+item.getDistance());
        });
        List<GeoRadiusResponse> collect = list.stream().filter(a ->
                new String(a.getMember()).contains(targetName)
        ).collect(Collectors.toList());
        System.out.println("过滤后...");
        collect.forEach(a->{
            System.out.println(new String(a.getMember())+a.getDistance());
        });
    }
}
