package com.turo.nasusweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by YQ950209 on 2017/2/14.
 */
//市列表用于存放某省下属各市
public class City extends DataSupport {

    private int id;

    private String cityName;//市名称

    private int cityCode;//市代号

    private int provinceId;//当前市所属省的id值

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
}
