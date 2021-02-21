package com.yy.utils;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * QQ地图定位服务
 * 根据地址返回经纬度：https://apis.map.qq.com/ws/geocoder/v1/?address=哈尔滨&key=OB4BZ-D4W3U-B7VVO-4PJWW-6TKDJ-WPB77
 * 根据经纬度返回周边：https://apis.map.qq.com/ws/geocoder/v1/?location=39.984154,116.307490&key=OB4BZ-D4W3U-B7VVO-4PJWW-6TKDJ-WPB77&get_poi=1
 * @author yuanyang
 * @date 2020-11-13
 */
@Slf4j
@Component
public class TencentGis {
    private final static String URLADDR = "/?address=";
    private final static String URLLOCATION = "?location=";
    private final static String URLKEY = "&key=";
    private final static String GET_POI = "&get_poi=1";
    private static JSONObject ERROR_JSON;
    static {
        ERROR_JSON = JSON.parseObject("{\"status\":1}");
    }

    @Value("${gis.tencent.url}")
    private String gisUrl;
    @Value("${gis.tencent.key}")
    private String key;
    @Value("${gis.tencent.def_addr}")
    private String defAddr;

    public JSONObject getLocationByAddress(String address) {

        StringBuffer url = new StringBuffer();
        url.append(gisUrl).append(URLADDR).append(address).append(URLKEY).append(key);
        try {
            String result1= HttpUtil.get(url.toString());
            JSONObject jsonObject = JSON.parseObject(result1);
            return jsonObject;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return ERROR_JSON;
    }

    /**
     * https://apis.map.qq.com/ws/geocoder/v1/?location=39.984154,116.307490&key=OB4BZ-D4W3U-B7VVO-4PJWW-6TKDJ-WPB77&get_poi=1
     * @param address
     * @return
     */
    public JSONObject getAddressListByLocation(String address) {
        try {
            if(StringUtils.isNotBlank(address)){
                address = address.trim();
            }else {
                address = "";
            }
            if(StringUtils.isNotBlank(defAddr)){
                address = defAddr + address;
            }
            JSONObject jsonObject = getLocationByAddress(address);
            JSONObject jsonLocation = jsonObject.getJSONObject("result").getJSONObject("location");
            String lng = jsonLocation.getString("lng");
            String lat = jsonLocation.getString("lat");
            StringBuffer url = new StringBuffer();
            url.append(gisUrl).append(URLLOCATION).append(lat).append(",").append(lng).append(URLKEY).append(key).append(GET_POI);
            String reStr = HttpUtil.get(url.toString());
            JSONObject json = JSON.parseObject(reStr);
            return json;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return ERROR_JSON;
    }
}
