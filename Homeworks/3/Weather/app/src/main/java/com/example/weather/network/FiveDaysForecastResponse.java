package com.example.weather.network;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;

public class FiveDaysForecastResponse {

    @SerializedName("list")
    private HourForecastInfo[] hourForecastInfos;

    @NonNull
    public HourForecastInfo[] getHourForecastInfos() {
        return hourForecastInfos;
    }
}
