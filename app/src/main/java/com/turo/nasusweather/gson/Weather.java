package com.turo.nasusweather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by chenzhiyuan on 2017/5/8.
 * 总的实例类用于引用刚刚创建的gson包下的各个实体类
 */

public class Weather {

    public String status;

    public Basic basic;

    public AQI aqi;

    public Now now;

    public Suggestion suggestion;

    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;
}
