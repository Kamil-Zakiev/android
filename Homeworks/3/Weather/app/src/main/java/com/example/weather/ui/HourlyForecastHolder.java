package com.example.weather.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weather.R;
import com.squareup.picasso.Picasso;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public final class HourlyForecastHolder extends RecyclerView.ViewHolder {
    private final Context context;

    @BindView(R.id.date)
    public TextView dateTw;

    @BindView(R.id.time)
    public TextView timeTextView;

    @BindView(R.id.temp)
    public TextView tempTextView;

    @BindView(R.id.weather_img)
    public ImageView imageView;

    public HourlyForecastHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        context = itemView.getContext();
    }

    public void SetData(HourlyForecast forecast) {
        dateTw.setText(forecast.getDate());
        timeTextView.setText(forecast.getTime());
        tempTextView.setText(context.getString(R.string.forecast_format, forecast.getTemp()));
        Picasso.get().load(forecast.getWeatherImgUri()).into(imageView);
    }
}
