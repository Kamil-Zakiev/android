package com.example.weather.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HourForecastInfo {
    @SerializedName("dt")
    private long UnixTimestamp;

    @SerializedName("main")
    private ForecastInfo forecastInfo;

    @SerializedName("weather")
    private List<WeatherInfo> weatherInfo;

    public ForecastInfo getForecastInfo() {
        return forecastInfo;
    }

    public List<WeatherInfo> getWeatherInfo() {
        return weatherInfo;
    }

    public long getUnixTimestamp() {
        return UnixTimestamp;
    }
}
