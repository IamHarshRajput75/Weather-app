package com.WeatherApp.WeatherApplication.DTO;

import java.util.List;

public class MyAppForecast {


    private WeatherResponse weatherResponse ;
    private List<DayTem> dayTem;



    public MyAppForecast(WeatherResponse weatherResponse, List<DayTem> dayTem) {
        this.weatherResponse = weatherResponse;
        this.dayTem = dayTem;
    }

    public MyAppForecast() {
    }

    public WeatherResponse getWeatherResponse() {
        return weatherResponse;
    }

    public void setWeatherResponse(WeatherResponse weatherResponse) {
        this.weatherResponse = weatherResponse;
    }

    public List<DayTem> getDayTem() {
        return dayTem;
    }

    public void setDayTem(List<DayTem> dayTem) {
        this.dayTem = dayTem;
    }
}
