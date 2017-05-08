package com.turo.nasusweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by YQ950209 on 2017/2/14.
 */
//省列表，用于存放全国各省数据
public class Province extends DataSupport {

    private int id;

    private String provinceName;//省名称

    private int provinceCode;//省代号

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }
}
