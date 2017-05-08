package com.turo.nasusweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chenzhiyuan on 2017/5/8.
 * 当前的天气信息类
 */

public class Now {

    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond")
    public More more;

    public class More {

        @SerializedName("txt")
        public String info;
    }
}
