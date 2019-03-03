package com.example.weather;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public final class DayForecastHolder extends RecyclerView.ViewHolder {
    private final Context context;

    private TextView dateTw;
    private TextView morningTw;
    private TextView afternoonTw;
    private TextView eveningTw;
    private TextView overnightTw;
    private ImageView imageView;

    public DayForecastHolder(@NonNull View itemView) {
        super(itemView);

        dateTw = itemView.findViewById(R.id.date);
        morningTw = itemView.findViewById(R.id.morning_temp);
        afternoonTw = itemView.findViewById(R.id.afternoon_temp);
        eveningTw = itemView.findViewById(R.id.evening_temp);
        overnightTw = itemView.findViewById(R.id.overnight_temp);
        imageView = itemView.findViewById(R.id.weather_img);
        context = itemView.getContext();
    }

    public void SetData(DayForecast forecast) {
        dateTw.setText(forecast.getDate());

        morningTw.setText(context.getString(R.string.morning_forecast, forecast.getMorning()));
        afternoonTw.setText(context.getString(R.string.afternoon_forecast, forecast.getAfternoon()));
        eveningTw.setText(context.getString(R.string.evening_forecast, forecast.getEvening()));
        overnightTw.setText(context.getString(R.string.overnight_forecast, forecast.getOvernight()));

        Picasso.get().load(forecast.getWeatherImgUri()).into(imageView);
    }
}
