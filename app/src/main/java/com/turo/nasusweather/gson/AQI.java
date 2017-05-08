package com.turo.nasusweather.gson;

/**
 * Created by chenzhiyuan on 2017/5/8.
 * 当前空气质量情况类
 */
public class AQI {

    public AQICity city;

    public class AQICity {

        public String aqi;

        public String pm25;
    }
}
