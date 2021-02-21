package com.yy;

import com.alibaba.fastjson.JSONObject;
import com.yy.utils.TencentGis;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author yuanyang
 * @version 1.0
 * @date 2021/2/7 17:47
 */
@SpringBootTest
public class TencentGisTest {
    @Autowired
    TencentGis tencentGis;
    @Test
    public void test(){
        System.out.println("test....");
        JSONObject json = tencentGis.getAddressListByLocation("鹏博");
        System.out.println(json);
    }
}
