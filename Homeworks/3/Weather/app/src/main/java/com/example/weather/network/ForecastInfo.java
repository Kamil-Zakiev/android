package com.example.weather.network;

import com.google.gson.annotations.SerializedName;

public class ForecastInfo {

    @SerializedName("temp")
    private float temp;

    public float getTemp() {
        return temp;
    }
}
