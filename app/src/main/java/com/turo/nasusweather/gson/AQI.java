package com.turo.nasusweather.gson;

/**
 * Created by chenzhiyuan on 2017/5/8.
 * 当前空气质量情况类
 */
public class AQI {

    public AQICity city;

    public class AQICity {

        public String qlty;//空气质量

        public String aqi;//AQI指数

        public String pm25;//pm2.5指数

        public String pm10;//pm10指数

        public String co;//一氧化碳指数

        public String no2;//二氧化氮指数

        public String so2;//二氧化硫指数
    }
}
