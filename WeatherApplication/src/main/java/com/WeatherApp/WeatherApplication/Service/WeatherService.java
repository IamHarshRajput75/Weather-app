package com.WeatherApp.WeatherApplication.Service;

import com.WeatherApp.WeatherApplication.DTO.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiUrl;

    @Value("${weather.api.forecast.url}")
     private String apiUrlForecast;

    RestTemplate restTemplate = new RestTemplate();


//    public String test(){
//        return "good boy";
//    }

    public WeatherResponse getData(String ci){
        String url = apiUrl + "?key="+apiKey+"&q="+ci;
        Root response = restTemplate.getForObject(url,Root.class);
        WeatherResponse weatherResponse  = new WeatherResponse();


        weatherResponse.setCity(response.getLocation().name);
        weatherResponse.setRegion(response.getLocation().region);
        weatherResponse.setCountry(response.getLocation().country);

        weatherResponse.setCondition(response.getCurrent().getCondition().getText());
        weatherResponse.setTemperature((response.getCurrent().getTemp_c()));

        return weatherResponse;
    }


    public MyAppForecast getForecast(String ci,int days){



    MyAppForecast myAppForecast = new MyAppForecast();
    WeatherResponse weatherResponse =   getData(ci);
    MyAppForecast response = new MyAppForecast();
    response.setWeatherResponse(weatherResponse);



     List<DayTem> dayList = new ArrayList<>();
     String forurl = apiUrlForecast + "?key="+apiKey+"&q="+ci+"&days="+ days;

        Root apiResponse = restTemplate.getForObject(forurl,Root.class);
        Forecast forecast = apiResponse.getForecast();

        ArrayList<Forecastday> forecastday = forecast.getForecastday();

        for(Forecastday rs: forecastday){

            DayTem d = new DayTem();
            d.setDate(rs.getDate());
            d.setMinTemp(rs.getDay().mintemp_c);
            d.setMaxTemp(rs.getDay().maxtemp_c);
            d.setAvgTemp(rs.getDay().avgtemp_c);
            dayList.add(d);
        }
        response.setDayTem(dayList);
        return  response;
    }





}
