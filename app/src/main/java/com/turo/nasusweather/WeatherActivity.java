package com.turo.nasusweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.turo.nasusweather.gson.Forecast;
import com.turo.nasusweather.gson.Weather;
import com.turo.nasusweather.service.AutoUpdateService;
import com.turo.nasusweather.util.HttpUtil;
import com.turo.nasusweather.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {

    private ScrollView weatherLayout;

    private TextView titleCity;

    private TextView titleUpdateTime;

    private TextView degreeText;//度数

    private TextView weatherInfoText;

    private TextView windDirectionText;

    private TextView windPowerText;

    private TextView humidityText;

    private TextView feelDegreeText;

    private TextView windSpeedText;

    private LinearLayout forecastLayout;

    private TextView aqiText;

    private TextView pm25Text;

    private TextView pm10Text;

    private TextView qualityText;

    private TextView coText;

    private TextView no2Text;

    private TextView so2Text;

    private TextView comfortText;

    private TextView derssText;

    private TextView coldText;

    private TextView uraysText;

    private TextView travelText;

    private TextView carWashText;

    private TextView sportText;

    private ImageView bingPicImg;

    public SwipeRefreshLayout swipeRefresh;

    public DrawerLayout drawerLayout;

    private Button navButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21 ){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_weather);
        //初始化各控件
        initView();

        //切换城市按钮点击监听事件
        initListener();

        SharedPreferences prefs = PreferenceManager.
                getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("weather",null);
        final String weatherId;
        if (weatherString != null){
            //有缓存时直接解析数据
            Weather weather = Utility.handleWeatherResponse(weatherString);
            weatherId = weather.basic.weatherId;
            showWeatherInfo(weather);
        }else{
            //无缓存时去服务器查询天气
            weatherId = getIntent().getStringExtra("weather_id");
            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(weatherId);
        }

        /**
         * 下拉刷新监听器
         */
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(weatherId);//下拉刷新回调方法，再次请求天气信息
            }
        });

        /**
         * 加载必应每日一图
         */
        String bingPic = prefs.getString("bing_pic",null);
        if (bingPic != null){
            Glide.with(this).load(bingPic).into(bingPicImg);
        }else{
            loadBingPic();
        }
    }

    private void initListener() {
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);//打开滑动菜单
            }
        });
    }


    /**
     * 根据天气id请求城市天气信息
     * @param weatherId
     */
    public void requestWeather(final String weatherId) {

        String weatherUrl = "http://guolin.tech/api/weather?cityid=" +
                weatherId + "&key=2744814cde4d4c8bb1f6d2711bc0dbdf";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this,"获取天气信息失败",
                                Toast.LENGTH_SHORT).show();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)){
                            SharedPreferences.Editor editor = PreferenceManager.
                                    getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather", responseText);
                            editor.apply();
                            showWeatherInfo(weather);
                            Intent intent = new Intent(WeatherActivity.this, AutoUpdateService.class);
                            startService(intent);
                        }else{
                            Toast.makeText(WeatherActivity.this,"获取天气信息失败",
                                    Toast.LENGTH_SHORT).show();
                        }
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });
        loadBingPic();
    }

    /**
     * 加载必应每日一图
     */
    private void loadBingPic(){
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.
                        getDefaultSharedPreferences(WeatherActivity.this).edit();
                editor.putString("bing_pic",bingPic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(bingPic).into
                                (bingPicImg);
                    }
                });
            }
        });
    }

    private void showWeatherInfo(Weather weather) {
        String cityName = weather.basic.cityName;
        String updateTime = weather.basic.update.updateTime.split(" ")[1];
        String degree = weather.now.temperature + "℃";
        String weatherInfo = weather.now.more.info;
        String windDirection = weather.now.wind.direction;
        String windPower = weather.now.wind.power + "级";
        String humidity = weather.now.humidity + "%";
        String feelTemprature = weather.now.feeling_temperature + "℃";
        String windSpeed = weather.now.wind.speed + "km/h";
        titleCity.setText(cityName);
        titleUpdateTime.setText(updateTime);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        windDirectionText.setText(windDirection);
        windPowerText.setText(windPower);
        humidityText.setText(humidity);
        feelDegreeText.setText(feelTemprature);
        windSpeedText.setText(windSpeed);
        forecastLayout.removeAllViews();
        for (Forecast forecast : weather.forecastList){
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item,
                    forecastLayout,false);
            TextView dateText = (TextView) view.findViewById(R.id.date_text);
            TextView infoText = (TextView) view.findViewById(R.id.info_text);
            TextView maxText = (TextView) view.findViewById(R.id.max_text);
            TextView minText = (TextView) view.findViewById(R.id.min_text);
            dateText.setText(forecast.date);
            infoText.setText(forecast.more.info);
            maxText.setText(forecast.temperature.max);
            minText.setText(forecast.temperature.min);
            forecastLayout.addView(view);
        }
        if (weather.aqi != null){
            aqiText.setText(weather.aqi.city.aqi);
            pm25Text.setText(weather.aqi.city.pm25);
            pm10Text.setText(weather.aqi.city.pm10);
            qualityText.setText(weather.aqi.city.qlty);
            coText.setText(weather.aqi.city.co != null ? weather.aqi.city.co : "0");
            no2Text.setText(weather.aqi.city.no2 != null ? weather.aqi.city.no2 : "0");
            so2Text.setText(weather.aqi.city.so2 != null ? weather.aqi.city.so2 : "0");
        }
        String comfort = "舒适度：" + weather.suggestion.comfort.info;
        String dress = "穿衣指数："+ weather.suggestion.dress.info;
        String cold = "感冒指数："+ weather.suggestion.cold.info;
        String urays = "紫外线指数："+ weather.suggestion.uRays.info;
        String travel = "旅游指数："+ weather.suggestion.travel.info;
        String carWash = "洗车指数："+ weather.suggestion.carWash.info;
        String sport = "运动建议："+ weather.suggestion.sport.info;
        comfortText.setText(comfort);
        derssText.setText(dress);
        coldText.setText(cold);
        uraysText.setText(urays);
        travelText.setText(travel);
        carWashText.setText(carWash);
        sportText.setText(sport);
        weatherLayout.setVisibility(View.VISIBLE);
    }

    private void initView() {
        weatherLayout = (ScrollView) findViewById(R.id.weather_layout);
        titleCity = (TextView) findViewById(R.id.title_city);
        titleUpdateTime = (TextView) findViewById(R.id.title_update_time);
        degreeText = (TextView) findViewById(R.id.degree_text);
        weatherInfoText = (TextView) findViewById(R.id.weather_info_text);
        windDirectionText = (TextView) findViewById(R.id.wind_dir);
        windPowerText = (TextView) findViewById(R.id.wind_pow);
        humidityText = (TextView) findViewById(R.id.tv_humidity);
        feelDegreeText = (TextView) findViewById(R.id.tv_feel_tmp);
        windSpeedText = (TextView) findViewById(R.id.tv_wind_speed);
        forecastLayout = (LinearLayout) findViewById(R.id.forecast_layout);
        aqiText = (TextView) findViewById(R.id.aqi_text);
        pm25Text = (TextView) findViewById(R.id.pm25_text);
        pm10Text = (TextView) findViewById(R.id.pm10_text);
        qualityText = (TextView) findViewById(R.id.qulity_text);
        coText = (TextView) findViewById(R.id.co_text);
        no2Text = (TextView) findViewById(R.id.no2_text);
        so2Text = (TextView) findViewById(R.id.so2_text);
        comfortText = (TextView) findViewById(R.id.comfort_text);
        derssText = (TextView) findViewById(R.id.dress_text);
        coldText = (TextView) findViewById(R.id.cold_text);
        uraysText = (TextView) findViewById(R.id.urays_text);
        travelText = (TextView) findViewById(R.id.travel_text);
        carWashText = (TextView) findViewById(R.id.car_wash_text);
        sportText = (TextView) findViewById(R.id.sport_text);
        bingPicImg = (ImageView) findViewById(R.id.bing_pic_img);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navButton = (Button) findViewById(R.id.nav_button);
    }
}