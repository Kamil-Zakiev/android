package com.example.weather.data;

import com.example.weather.network.ApiFactory;
import com.example.weather.network.IWeatherClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class RepositoryProvider {
    private static volatile RepositoryProvider sInstance;

    @Nullable
    private WeatherRepository weatherRepository;

    private RepositoryProvider() {
    }

    public static RepositoryProvider get() {
        if (sInstance == null) {
            synchronized (RepositoryProvider.class) {
                if (sInstance == null) {
                    sInstance = new RepositoryProvider();
                }
            }
        }
        return sInstance;
    }

    @NonNull
    private static <T> T getServiceInstance(Class<T> clazz) {
        return ApiFactory.getRetrofitInstance().create(clazz);
    }

    @NonNull
    public WeatherRepository provideWeatherRepository() {
        if (weatherRepository == null) {
            weatherRepository = new WeatherRepository(getServiceInstance(IWeatherClient.class));
        }
        return weatherRepository;
    }
}

