package com.example.weather;

public final class DayForecast {
    private final String date;
    private final int morning;
    private final int evening;
    private final int overnight;
    private String weatherImgUri;

    public DayForecast(String date, int morning, int afternoon, int evening, int overnight, String weatherImgUri) {

        this.date = date;
        this.morning = morning;
        this.afternoon = afternoon;
        this.evening = evening;
        this.overnight = overnight;
        this.weatherImgUri = weatherImgUri;
    }

    public String getDate() {
        return date;
    }

    public int getMorning() {
        return morning;
    }

    public int getAfternoon() {
        return afternoon;
    }

    private final int afternoon;

    public int getEvening() {
        return evening;
    }

    public int getOvernight() {
        return overnight;
    }

    public String getWeatherImgUri() {
        return weatherImgUri;
    }
}
