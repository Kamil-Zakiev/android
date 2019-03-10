package com.example.weather.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.weather.R;
import com.example.weather.data.RepositoryProvider;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    public static final String SELECTED_CITY = "selected_city";

    @BindView(R.id.weather_rw)
    RecyclerView weatherRw;

    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @BindView(R.id.empty_view)
    public TextView noForecastTw;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        swipeRefreshLayout.setOnRefreshListener(this);

        toolbar.setTitle(getCityName());
        setSupportActionBar(toolbar);

        weatherRw.setLayoutManager(new LinearLayoutManager(this));

        // todo: implement DB call
        HourlyForecast[] dbData = GetTestData();
        weatherRw.setAdapter(new WeatherForecastsAdapter(dbData));

        InvalidateData();
    }

    private String getCityName() {
        String cityName = getPreferences(MODE_PRIVATE).getString(SELECTED_CITY, null);
        if (cityName == null){
            cityName = getResources().getString(R.string.default_city);
        }
        return cityName;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != R.id.editCity) {
            return true;
        }

        EditText input = new EditText(this);
        final int id = 123;
        input.setId(id);

        new AlertDialog.Builder(this)
                .setView(input)
                .setTitle(R.string.change_city)
                .setPositiveButton(R.string.ok_btn, (dialog, which) -> {
                    EditText input1 = ((AlertDialog) dialog).findViewById(id);
                    Editable value = input1.getText();

                    String newCity = value.toString();
                    if (newCity.isEmpty() || newCity.contentEquals(toolbar.getTitle())) {
                        return;
                    }

                    saveCityName(newCity);
                    toolbar.setTitle(newCity);

                    InvalidateData();
                })
                .setNegativeButton(R.string.cancel_btn, (dialog, which) -> {
                    // do nothing
                })
                .show();
        return true;
    }

    private void saveCityName(String newCity) {
        getPreferences(MODE_PRIVATE)
                .edit()
                .putString(SELECTED_CITY, newCity)
                .apply();
    }

    private HourlyForecast[] GetTestData() {
        int i = -10;
        String testUrl = "https://cdn1.iconfinder.com/data/icons/weather-forecast-meteorology-color-1/128/weather-partly-cloudy-512.png";
        return new HourlyForecast[]{
                new HourlyForecast("Day 1", "12:00", i++, testUrl),
                new HourlyForecast("Day 1", "15:00", i++, testUrl),
                new HourlyForecast("Day 1", "18:00", i++, testUrl),
                new HourlyForecast("Day 1", "21:00", i++, testUrl),
                new HourlyForecast("Day 2", "00:00", i++, testUrl),
                new HourlyForecast("Day 2", "03:00", i++, testUrl),
                new HourlyForecast("Day 2", "06:00", i++, testUrl),
                new HourlyForecast("Day 2", "09:00", i++, testUrl),
                new HourlyForecast("Day 2", "12:00", i++, testUrl),
                new HourlyForecast("Day 2", "15:00", i++, testUrl),
                new HourlyForecast("Day 2", "18:00", i++, testUrl),
                new HourlyForecast("Day 3", "21:00", i++, testUrl),
                new HourlyForecast("Day 3", "00:00", i++, testUrl),
                new HourlyForecast("Day 3", "03:00", i++, testUrl),
                new HourlyForecast("Day 3", "06:00", i++, testUrl),
                new HourlyForecast("Day 3", "09:00", i++, testUrl),
                new HourlyForecast("Day 3", "12:00", i++, testUrl),
        };
    }

    @Override
    public void onRefresh() {
        InvalidateData();
    }

    private void InvalidateData() {
        new LoadWeatherForecast().execute();
    }

    private class LoadWeatherForecast extends AsyncTask<Void, Void, HourlyForecast[]>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (!swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(true);
            }
        }

        @Override
        protected HourlyForecast[] doInBackground(Void... voids) {
            return RepositoryProvider.get().provideWeatherRepository().getFiveDaysForecastFor(getCityName());
        }

        @Override
        protected void onPostExecute(@Nullable HourlyForecast[] forecasts) {
            if (forecasts == null || forecasts.length == 0) {
                weatherRw.setVisibility(View.GONE);
                noForecastTw.setVisibility(View.VISIBLE);
            } else {
                weatherRw.setVisibility(View.VISIBLE);
                noForecastTw.setVisibility(View.GONE);
                weatherRw.setAdapter(new WeatherForecastsAdapter(forecasts));
            }

            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
