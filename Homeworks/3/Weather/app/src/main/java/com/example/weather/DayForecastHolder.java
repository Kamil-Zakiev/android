package com.example.weather;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public final class DayForecastHolder extends RecyclerView.ViewHolder {
    private final Context context;

    @BindView(R.id.date)
    public TextView dateTw;

    @BindView(R.id.morning_temp)
    public TextView morningTw;

    @BindView(R.id.afternoon_temp)
    public TextView afternoonTw;

    @BindView(R.id.evening_temp)
    public TextView eveningTw;

    @BindView(R.id.overnight_temp)
    public TextView overnightTw;

    @BindView(R.id.weather_img)
    public ImageView imageView;

    public DayForecastHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

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
