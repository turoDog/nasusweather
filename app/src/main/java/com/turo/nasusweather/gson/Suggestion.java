package com.turo.nasusweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chenzhiyuan on 2017/5/8.
 * 天气相关生活建议类
 */

public class Suggestion {

    @SerializedName("comf")
    public Comfort comfort;//舒适度指数

    @SerializedName("cw")
    public CarWash carWash;//洗东西指数

    public Sport sport;//运动指数

    public class Comfort{

        @SerializedName("txt")
        public String info;
    }

    public class CarWash {

        @SerializedName("txt")
        public String info;
    }

    public class Sport {

        @SerializedName("txt")
        public String info;
    }
}
