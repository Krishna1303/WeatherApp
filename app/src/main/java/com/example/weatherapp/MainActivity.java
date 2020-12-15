package com.example.weatherapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.sql.Date;
import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public String baseUrl = "http://api.openweathermap.org/";
    public String appid = "a599f89eef5611d71715098cc52b01df";
    public String city = "Hyderabad";
    public SearchView sv ;
    public ImageView tempImage;
    public TextView date, location, maxTemp, minTemp, temp, tempType, pressure, humidity, sunRise, sunSet, coord,speed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        date = findViewById(R.id.id_date);
        location = findViewById(R.id.id_location);
        speed = findViewById(R.id.id_speed);
        maxTemp = findViewById(R.id.id_maxTemp);
        minTemp = findViewById(R.id.id_minTemp);
        temp = findViewById(R.id.id_temp);
        tempType = findViewById(R.id.id_tempType);
        pressure = findViewById(R.id.id_pressure);
        humidity = findViewById(R.id.id_humidity);
        sunRise = findViewById(R.id.id_sunrise);
        sunSet = findViewById(R.id.id_sunset);
        coord = findViewById(R.id.id_coord);
        tempImage = findViewById(R.id.id_tempImage);
        getData(city);
        sv = findViewById(R.id.id_btn);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getData(sv.getQuery().toString());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    void getData(String city){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherInterface w = retrofit.create(WeatherInterface.class);
        Call<WeatherResult> call = w.getCurrentWeather(city,appid);
        call.enqueue(new Callback<WeatherResult>() {
            @Override
            public void onResponse(Call<WeatherResult> call, Response<WeatherResult> response) {
                if (response.code() == 200) {
                    WeatherResult weatherResult = response.body();
                    date.setText(new SimpleDateFormat().format(new Date(weatherResult.date*1000)));
                    speed.setText(String.format("Speed is %s", weatherResult.wind.speed));
                    location.setText(String.format("%s,%s", weatherResult.name, weatherResult.sys.country));
                    maxTemp.setText(String.format("Maximum Temperature %s °C", weatherResult.main.maxTemperature));
                    minTemp.setText(String.format("Minimum Temperature is %s °C", weatherResult.main.minTemperature));
                    temp.setText(String.format("%d °C", Math.round(weatherResult.main.temperatue - 273.15)));
                    tempType.setText(weatherResult.list.get(0).skyType);
                    pressure.setText(String.format("Presssure %s", weatherResult.main.pressure));
                    humidity.setText(String.format("Humidity  %s", weatherResult.main.humidity));
                    sunRise.setText(String.format("Sunrise %s",getDate(weatherResult.sys.sunRise)));
                    sunSet.setText(String.format("Sunset %s", getDate(weatherResult.sys.sunSet)));
                    coord.setText(String.format("Lat | Long %s|%s", weatherResult.coor.latitude, weatherResult.coor.latitude));
                    String image="https://openweathermap.org/img/w/"+weatherResult.list.get(0).image+".png";
                    Glide.with(MainActivity.this).load(image).into(tempImage);
                }
            }
            @Override
            public void onFailure(Call<WeatherResult> call, Throwable t) {
                    Log.e("error",t.getMessage());
            }
        });
}
    public String getDate(Long l ){
        SimpleDateFormat s  = new SimpleDateFormat();
        Date d = new Date(l*1000);
        String str = s.format(d);
        return str.substring(10);
    }
}