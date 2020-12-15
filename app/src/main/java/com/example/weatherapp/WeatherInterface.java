package com.example.weatherapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherInterface {
    @GET("data/2.5/weather?")
    Call<WeatherResult> getCurrentWeather(@Query("q") String city ,@Query("appid") String appid);
}
