package com.example.weather.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IWeatherClient {

    @GET("/data/2.5/forecast?units=metric")
    Call<FiveDaysForecastResponse> getFiveDaysForecastByCityName(@Query("q") String cityName);
}
