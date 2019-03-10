package com.example.weather.ui;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.weather.R;
import com.example.weather.data.HourForecastContract;
import com.example.weather.data.HourlyForecastDbHelper;
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

    private HourlyForecastDbHelper hourlyForecastDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        hourlyForecastDbHelper = new HourlyForecastDbHelper(this);

        toolbar.setTitle(getCityName());
        setSupportActionBar(toolbar);

        weatherRw.setLayoutManager(new LinearLayoutManager(this));
        weatherRw.setAdapter(new WeatherForecastsAdapter(GetDbData()));

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

    private HourlyForecast[] GetDbData() {
        SQLiteDatabase db = hourlyForecastDbHelper.getReadableDatabase();
        String[] projection = {
                HourForecastContract.HourForecastEntry.COLUMN_DAY,
                HourForecastContract.HourForecastEntry.COLUMN_TIME,
                HourForecastContract.HourForecastEntry.COLUMN_TEMP,
                HourForecastContract.HourForecastEntry.COLUMN_ICON };

        Cursor cursor = db.query(
                HourForecastContract.HourForecastEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        int count = cursor.getCount();
        if(count==0) {
            return new HourlyForecast[0];
        }

        HourlyForecast[] result = new HourlyForecast[count];

        int dayColumnIndex = cursor.getColumnIndex(HourForecastContract.HourForecastEntry.COLUMN_DAY);
        int timeColumnIndex = cursor.getColumnIndex(HourForecastContract.HourForecastEntry.COLUMN_TIME);
        int tempColumnIndex = cursor.getColumnIndex(HourForecastContract.HourForecastEntry.COLUMN_TEMP);
        int iconColumnIndex = cursor.getColumnIndex(HourForecastContract.HourForecastEntry.COLUMN_ICON );
        int i = 0;
        while (cursor.moveToNext()) {
            result[i] = new HourlyForecast(
                    cursor.getString(dayColumnIndex),
                    cursor.getString(timeColumnIndex),
                    cursor.getFloat(tempColumnIndex),
                    cursor.getString(iconColumnIndex));
            i++;
        }
        cursor.close();

        return result;
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
            HourlyForecast[] forecasts = RepositoryProvider.get().provideWeatherRepository().getFiveDaysForecastFor(getCityName());

            SQLiteDatabase db = hourlyForecastDbHelper.getWritableDatabase();
            db.delete(HourForecastContract.HourForecastEntry.TABLE_NAME, null, null);

            if (forecasts != null && forecasts.length != 0) {
                for (HourlyForecast f : forecasts) {
                    ContentValues values = new ContentValues();
                    values.put(HourForecastContract.HourForecastEntry.COLUMN_DAY, f.getDate());
                    values.put(HourForecastContract.HourForecastEntry.COLUMN_TIME, f.getTime());
                    values.put(HourForecastContract.HourForecastEntry.COLUMN_TEMP, f.getTemp());
                    values.put(HourForecastContract.HourForecastEntry.COLUMN_ICON, f.getWeatherImgUri());

                    db.insert(HourForecastContract.HourForecastEntry.TABLE_NAME, null, values);
                }
            }
            return forecasts;
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
