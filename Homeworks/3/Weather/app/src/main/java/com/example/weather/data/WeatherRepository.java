package com.example.weather.data;

import android.text.format.DateFormat;

import com.example.weather.network.FiveDaysForecastResponse;
import com.example.weather.network.HourForecastInfo;
import com.example.weather.network.IWeatherClient;
import com.example.weather.ui.HourlyForecast;

import java.io.IOException;
import java.util.Date;

import androidx.annotation.NonNull;

public class WeatherRepository implements IWeatherRepository {
    private IWeatherClient weatherClient;

    public WeatherRepository(IWeatherClient weatherClient) {
        this.weatherClient = weatherClient;
    }

    @Override
    public HourlyForecast[] getFiveDaysForecastFor(@NonNull String city) {
        try {
            FiveDaysForecastResponse response = weatherClient
                    .getFiveDaysForecastByCityName(city+",ru")
                    .execute()
                    .body();

            if (response == null){
                return null;
            }

            String urlBase = "http://openweathermap.org/img/w/";
            HourForecastInfo[] hourlyInfos = response.getHourForecastInfos();
            HourlyForecast[] hourlyForecast = new HourlyForecast[hourlyInfos.length];
            for (int i = 0; i < hourlyInfos.length; i++){
                String iconId = hourlyInfos[i].getWeatherInfo().get(0).getIconUrl();

                Date d = new Date(hourlyInfos[i].getUnixTimestamp()*1000);
                String day = DateFormat.format("dd.MM.yyyy", d).toString();
                String time = DateFormat.format("HH:mm", d).toString();

                hourlyForecast[i]=new HourlyForecast(day, time, hourlyInfos[i].getForecastInfo().getTemp(), urlBase + iconId + ".png");
            }

            return hourlyForecast;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
