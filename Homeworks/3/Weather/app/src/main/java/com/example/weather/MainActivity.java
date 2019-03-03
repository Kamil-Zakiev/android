package com.example.weather;

import android.content.DialogInterface;
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

public class MainActivity extends AppCompatActivity {
    RecyclerView weatherRw;
    private Toolbar toolbar;
    private TextView noForecastTw;

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
        weatherRw.setAdapter(new WeatherForecastsAdapter(GetTestData()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != R.id.editCity){
            return true;
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        EditText input = new EditText(this);
        final int id = 123;
        input.setId(id);
        alert.setView(input);

        alert.setTitle(R.string.change_city);

        alert.setPositiveButton(R.string.ok_btn, new DialogInterface.OnClickListener() {
            //@Override
            public void onClick(DialogInterface dialog, int which) {
                EditText input = ((AlertDialog)dialog).findViewById(id);
                Editable value = input.getText();

                String newCity = value.toString();
                if (newCity.isEmpty() || newCity.contentEquals(toolbar.getTitle())) {
                    return;
                }

                weatherRw.setVisibility(View.GONE);
                noForecastTw .setVisibility(View.VISIBLE);

                toolbar.setTitle(newCity);
            }
        });

        alert.setNegativeButton(R.string.cancel_btn, new DialogInterface.OnClickListener() {
            //@Override
            public void onClick(DialogInterface dialog, int which) {
                // do nothing
            }
        });

        alert.show();
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
}
