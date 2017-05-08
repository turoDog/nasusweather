package com.turo.nasusweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chenzhiyuan on 2017/5/8.
 * 由于JSON中的一些字段不太适合直接作为java字段来命名，
 * 因此使用@SerializeName注解的方式来让JSON字段和Java字段之间建立映射关系。
 */

public class Basic {

    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class Update {

        @SerializedName("loc")
        public String updateTime;
    }
}
