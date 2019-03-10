package com.example.weather.data;

import com.example.weather.ui.HourlyForecast;

import androidx.annotation.NonNull;

public interface IWeatherRepository {
    HourlyForecast[] getFiveDaysForecastFor(@NonNull String city);
}
