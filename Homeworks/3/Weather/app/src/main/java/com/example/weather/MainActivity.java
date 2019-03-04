package com.example.weather;

import android.content.DialogInterface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView weatherRw;
    private Toolbar toolbar;
    private TextView noForecastTw;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        noForecastTw = findViewById(R.id.empty_view);

        toolbar.setTitle(R.string.default_city);
        setSupportActionBar(toolbar);

        weatherRw = findViewById(R.id.weather_rw);
        weatherRw.setLayoutManager(new LinearLayoutManager(this));

        // todo: implement city retrieval


        // todo: implement DB call
        DayForecast[] dbData = GetTestData();
        weatherRw.setAdapter(new WeatherForecastsAdapter(dbData));

        // todo: initiate API call and further invalidation

        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
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
        new AlertDialog.Builder(this)
                .setView(input)
                .setTitle(R.string.change_city)
                .setPositiveButton(R.string.ok_btn, new DialogInterface.OnClickListener() {
                    //@Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText input = ((AlertDialog) dialog).findViewById(id);
                        Editable value = input.getText();

                        String newCity = value.toString();
                        if (newCity.isEmpty() || newCity.contentEquals(toolbar.getTitle())) {
                            return;
                        }

                        toolbar.setTitle(newCity);

                        // todo: implement city name saving in shared preferences

                        InvalidateData();
                    }
                })
                .setNegativeButton(R.string.cancel_btn, new DialogInterface.OnClickListener() {
                    //@Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
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
