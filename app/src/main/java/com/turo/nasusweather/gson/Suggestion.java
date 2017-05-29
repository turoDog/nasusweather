package com.turo.nasusweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chenzhiyuan on 2017/5/8.
 * 天气相关生活建议类
 */

public class Suggestion {

    @SerializedName("comf")
    public Comfort comfort;//舒适度指数

    @SerializedName("drsg")//穿衣指数
    public Dress dress;

    @SerializedName("flu")//感冒指数
    public Cold cold;

    @SerializedName("uv")//紫外线指数
    public URays uRays;

    @SerializedName("trav")//旅游指数
    public Travel travel;

    @SerializedName("cw")
    public CarWash carWash;//洗车指数

    public Sport sport;//运动指数

    public class Comfort{
        @SerializedName("txt")
        public String info;
    }

    public class Dress{
        @SerializedName("txt")
        public String info;
    }

    public class Cold{
        @SerializedName("txt")
        public String info;
    }

    public class URays{
        @SerializedName("txt")
        public String info;
    }

    public class Travel {
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
