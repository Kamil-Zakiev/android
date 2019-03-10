package com.example.weather.ui;

public final class HourlyForecast {
    private final String date;
    private String time;
    private float temp;
    private String weatherImgUri;

    public HourlyForecast(String date, String time, float temp, String weatherImgUri) {

        this.date = date;
        this.time = time;
        this.temp = temp;
        this.weatherImgUri = weatherImgUri;
    }

    public String getDate() {
        return date;
    }

    public String getWeatherImgUri() {
        return weatherImgUri;
    }

    public String getTime() {
        return time;
    }

    public float getTemp() {
        return temp;
    }
}
