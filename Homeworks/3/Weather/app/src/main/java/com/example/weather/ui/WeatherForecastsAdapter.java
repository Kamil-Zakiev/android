package com.example.weather.ui;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weather.R;

import androidx.recyclerview.widget.RecyclerView;


public final class WeatherForecastsAdapter extends RecyclerView.Adapter<HourlyForecastHolder> {
    private HourlyForecast[] forecasts;

    public WeatherForecastsAdapter(HourlyForecast[] forecasts){
        this.forecasts = forecasts;
    }

    @NonNull
    @Override
    public HourlyForecastHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.day_forecast_card, viewGroup, false);
        return new HourlyForecastHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyForecastHolder hourlyForecastHolder, int i) {
        hourlyForecastHolder.SetData(forecasts[i]);
    }

    @Override
    public int getItemCount() {
        return forecasts.length;
    }
}
