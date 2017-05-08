package com.turo.nasusweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chenzhiyuan on 2017/5/8.
 * 未来几天的天气信息
 * daily_forecast包含的是一个数组。数组中的每一项代表着未来
 * 一天的天气信息，所以我们只需要定义出单日天气的实体类。
 */

public class Forecast {

    public String date;

    @SerializedName("tmp")
    public Temperature temperature;

    @SerializedName("cond")
    public More more;

    public class Temperature {

        public String max;

        public String min;
    }

    public class More{

        @SerializedName("txt_d")
        public String info;
    }

}
