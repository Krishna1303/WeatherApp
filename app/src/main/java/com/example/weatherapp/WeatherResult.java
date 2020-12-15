package com.example.weatherapp;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WeatherResult {


    
    @SerializedName("coord")
    public Coord coor;
    @SerializedName("weather")
    public ArrayList<Weather> list = new ArrayList<>();
    @SerializedName("main")
    public Main main;
    @SerializedName("wind")
    public Wind wind;
    @SerializedName("sys")
    public Sys sys;
    @SerializedName("name")
    public String name;
    @SerializedName("dt")
    public Long date;
}
class Coord{
    @SerializedName("lon")
    public String longitude;
    @SerializedName("lat")
    public String latitude;
}
class Weather{
    @SerializedName("main")
    public String skyType;
    @SerializedName("icon")
    public String image;
}
class Main{
    @SerializedName("temp")
    public Double temperatue;
    @SerializedName("temp_min")
    public Double minTemperature;
    @SerializedName("temp_max")
    public Double maxTemperature;
    @SerializedName("pressure")
    public Double pressure;
    @SerializedName("humidity")
    public Double humidity;
}
class Wind{
    @SerializedName("speed")
    public String speed;
}
class Sys{
    @SerializedName("country")
    public String country;
    @SerializedName("sunrise")
    public Long sunRise;
    @SerializedName("sunset")
    public Long sunSet;
}

