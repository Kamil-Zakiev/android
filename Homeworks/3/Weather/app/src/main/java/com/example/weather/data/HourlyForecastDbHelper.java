package com.example.weather.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class HourlyForecastDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "hourly_forecast.db";
    private static final int DATABASE_VERSION = 1;

    public HourlyForecastDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_FORECASTS_TABLE = "CREATE TABLE " + HourForecastContract.HourForecastEntry.TABLE_NAME + " ("
                + HourForecastContract.HourForecastEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HourForecastContract.HourForecastEntry.COLUMN_DAY + " TEXT NOT NULL, "
                + HourForecastContract.HourForecastEntry.COLUMN_TIME + " TEXT NOT NULL, "
                + HourForecastContract.HourForecastEntry.COLUMN_TEMP + " real NOT NULL, "
                + HourForecastContract.HourForecastEntry.COLUMN_ICON + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_FORECASTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // not supported
    }
}
