package com.example.weather.data;

import android.provider.BaseColumns;

public class HourForecastContract {
    private HourForecastContract(){
    }

    public static final class HourForecastEntry implements BaseColumns{
        public final static String TABLE_NAME = "hourly_forecasts";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_DAY = "day";
        public final static String COLUMN_TIME = "time";
        public final static String COLUMN_TEMP = "temp";
        public final static String COLUMN_ICON = "iconUrl";
    }
}
