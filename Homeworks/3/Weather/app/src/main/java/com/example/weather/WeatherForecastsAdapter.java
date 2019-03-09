package com.example.weather;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;


public final class WeatherForecastsAdapter extends RecyclerView.Adapter<DayForecastHolder> {
    private DayForecast[] forecasts;

    public WeatherForecastsAdapter(DayForecast[] forecasts){
        this.forecasts = forecasts;
    }

    @NonNull
    @Override
    public DayForecastHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.day_forecast_card, viewGroup, false);
        return new DayForecastHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayForecastHolder dayForecastHolder, int i) {
        dayForecastHolder.SetData(forecasts[i]);
    }

    @Override
    public int getItemCount() {
        return forecasts.length;
    }
}
