package com.WeatherApp.WeatherApplication.DTO;

import java.util.ArrayList;

public class Forecast {
    public ArrayList<Forecastday> forecastday;

    public void setForecastday(ArrayList<Forecastday> forecastday){
        this.forecastday = forecastday;
    }

    public ArrayList<Forecastday> getForecastday() {
        return forecastday;
    }
}
