package com.example.weather.network;

import com.example.weather.BuildConfig;

import java.io.IOException;

import androidx.annotation.NonNull;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public final class WeatherApiKeyInterceptor implements Interceptor{
    @NonNull
    @Override
    public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
        final Request original = chain.request();
        final HttpUrl originalHttpUrl = original.url();
        final HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter("appid", BuildConfig.WEATHER_API_KEY)
                .build();

        return chain.proceed(original.newBuilder().url(url).build());
    }
}
