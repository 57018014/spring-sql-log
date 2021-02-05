package com.yy.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.*;
import redis.clients.jedis.params.GeoRadiusParam;

import java.util.List;
import java.util.Map;

/**
 * redis距离计算工具类
 */
@Data
@Slf4j
@Component
public class RedisGeoUtil {
    @Autowired
    private JedisPool jedisPool;

    public void release(Jedis jedis){
        if(jedis != null){
            jedis.close();
        }
    }

    /**
     * 增加地理位置的坐标
     * @param key
     * @param coordinate
     * @param memberName
     * @return
     */
    public Long geoadd(String key, GeoCoordinate coordinate, String memberName, int indexdb){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            jedis.select(indexdb);
            return jedis.geoadd(key, coordinate.getLongitude(), coordinate.getLatitude(), memberName);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            release(jedis);
        }
        return null;
    }

    /**
     * 批量增加地理位置
     * @param key
     * @param memberCoordinate
     * @return
     */
    public Long geoadd(String key, Map<String, GeoCoordinate> memberCoordinate, int indexdb){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            jedis.select(indexdb);
            return jedis.geoadd(key, memberCoordinate);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            release(jedis);
        }
        return null;
    }

    /**
     * 根据给定地理位置坐标获取指定范围内的地理位置集合（返回匹配位置的经纬度 + 匹配位置与给定地理位置的距离 + 从近到远排序）
     * @param key
     * @param coordinate
     * @param radius
     * @return
     */
    public List<GeoRadiusResponse> geoRadius(String key, GeoCoordinate coordinate, double radius, int indexdb){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            jedis.select(indexdb);
            return jedis.georadius(key, coordinate.getLongitude(), coordinate.getLatitude(), radius, GeoUnit.KM, GeoRadiusParam.geoRadiusParam().withDist().withCoord().sortAscending());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            release(jedis);
        }
        return null;
    }

    /**
     * 根据给定地理位置获取指定范围内的地理位置集合（返回匹配位置的经纬度 + 匹配位置与给定地理位置的距离 + 从近到远排序）
     * @param key
     * @param member
     * @param radius
     * @return
     */
    public List<GeoRadiusResponse> geoRadiusByMember(String key, String member, double radius, int indexdb){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            jedis.select(indexdb);
            return jedis.georadiusByMember(key, member, radius, GeoUnit.KM, GeoRadiusParam.geoRadiusParam().withDist().withCoord().sortAscending());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            release(jedis);
        }
        return null;
    }


    /**
     * 查询两位置距离
     * @param key
     * @param member1
     * @param member2
     * @param unit
     * @return
     */
    public Double geoDist(String key, String member1, String member2, GeoUnit unit, int indexdb){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            jedis.select(indexdb);
            return jedis.geodist(key, member1, member2, unit);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            release(jedis);
        }
        return null;
    }


    /**
     * 可以获取某个地理位置的geohash值
     * @param key
     * @param members
     * @return
     */
    public List<String> geoHash(String key, int indexdb, String... members){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            jedis.select(indexdb);
            return jedis.geohash(key, members);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            release(jedis);
        }
        return null;
    }


    /**
     * 获取地理位置的坐标
     * @param key
     * @param members
     * @return
     */
    public List<GeoCoordinate> geoPos(String key, int indexdb, String... members){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            jedis.select(indexdb);
            return jedis.geopos(key, members);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            release(jedis);
        }
        return null;
    }
    /**
     * @Description: 删除位置信息
     * @Author: YLsTone
     * @Date: 2021/1/29 15:55
     * @Param: key
     * @Param: indexdb
     * @Param: members
     * @return: java.lang.Long
     **/
    public Long geodel(String key,int indexdb,String... members) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(indexdb);
            return jedis.zrem(key,members);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            release(jedis);
        }
        return null;
    }




}
