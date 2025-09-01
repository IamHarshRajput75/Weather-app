package com.WeatherApp.WeatherApplication.controller;

import com.WeatherApp.WeatherApplication.DTO.MyAppForecast;
import com.WeatherApp.WeatherApplication.DTO.WeatherResponse;
import com.WeatherApp.WeatherApplication.Service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
@CrossOrigin
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

//    @GetMapping("/{city}")
//    public String getWeatherData(@PathVariable String city){
//        return weatherService.test();
//    }

    @GetMapping("/my/{city}")
    public WeatherResponse getWeather(@PathVariable String city){
      return weatherService.getData(city);
    }

    @GetMapping("/forecast")

    public MyAppForecast getForecast(@RequestParam String city , @RequestParam int days){
        return weatherService.getForecast(city, days);
    }


}
