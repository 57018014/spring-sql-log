package com.yy.comfig.gis;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author yuanyang
 * @date 2020-11-18
 */
@Data
public class GisVO {
    private String address;
    /**
     * 经度
     */
    private BigDecimal lng;
    /**
     * 纬度
     */
    private BigDecimal lat;

}
