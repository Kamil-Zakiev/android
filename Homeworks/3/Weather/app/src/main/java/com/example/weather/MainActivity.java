package com.example.weather;

import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

        String cityName = getPreferences(MODE_PRIVATE).getString(SELECTED_CITY, null);
        if (cityName == null){
            cityName = getResources().getString(R.string.default_city);
        }

        toolbar.setTitle(cityName);
        setSupportActionBar(toolbar);

        weatherRw.setLayoutManager(new LinearLayoutManager(this));

        // todo: implement DB call
        DayForecast[] dbData = GetTestData();
        weatherRw.setAdapter(new WeatherForecastsAdapter(dbData));

        // todo: initiate API call and further invalidation

        swipeRefreshLayout.setOnRefreshListener(this);
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
        final MainActivity mainActivity = this;
        //@Override
        //@Override
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

                    mainActivity.getPreferences(MODE_PRIVATE)
                            .edit()
                            .putString(SELECTED_CITY, newCity)
                            .apply();

                    toolbar.setTitle(newCity);

                    InvalidateData();
                })
                .setNegativeButton(R.string.cancel_btn, (dialog, which) -> {
                    // do nothing
                })
                .show();
        return true;
    }

    private DayForecast[] GetTestData() {
        int i = 0;
        String testUrl = "https://cdn1.iconfinder.com/data/icons/weather-forecast-meteorology-color-1/128/weather-partly-cloudy-512.png";
        return new DayForecast[]{
                new DayForecast("Day 1", i++, i++, i++, i++, testUrl),
                new DayForecast("Day 2", i++, i++, i++, i++, testUrl),
                new DayForecast("Day 3", i++, i++, i++, i++, testUrl),
                new DayForecast("Day 4", i++, i++, i++, i++, testUrl),
                new DayForecast("Day 5", i++, i++, i++, i++, testUrl),
                new DayForecast("Day 6", i++, i++, i++, i++, testUrl),
                new DayForecast("Day 7", i++, i++, i++, i++, testUrl),
                new DayForecast("Day 8", i++, i++, i++, i++, testUrl),
                new DayForecast("Day 9", i++, i++, i++, i++, testUrl),
                new DayForecast("Day 10", i++, i++, i++, i++, testUrl),
                new DayForecast("Day 11", i++, i++, i++, i++, testUrl),
                new DayForecast("Day 12", i++, i++, i++, i++, testUrl),
                new DayForecast("Day 13", i++, i++, i++, i++, testUrl),
                new DayForecast("Day 14", i++, i++, i++, i++, testUrl),
                new DayForecast("Day 15", i++, i++, i++, i++, testUrl),
                new DayForecast("Day 16", i++, i++, i++, i++, testUrl),
                new DayForecast("Day 17", i++, i++, i++, i++, testUrl),
        };
    }

    @Override
    public void onRefresh() {
        InvalidateData();
    }

    private void InvalidateData() {
        if (!swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(true);
        }

        String cityName = toolbar.getTitle().toString();
        DayForecast[] data = GetTestData();

        if (data.length == 0) {
            weatherRw.setVisibility(View.GONE);
            noForecastTw.setVisibility(View.VISIBLE);
        } else {
            weatherRw.setVisibility(View.VISIBLE);
            noForecastTw.setVisibility(View.GONE);
            weatherRw.setAdapter(new WeatherForecastsAdapter(data));
        }

        swipeRefreshLayout.setRefreshing(false);
    }
}
