package com.example.mushroomapi.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;


@Component
public class AccuWeatherUriBuilder {


    private final String API_KEY = "OJT7tYGziR9z19njtdykrrQr2FZpSiKi";
    String createLocationRequestUri(String location) throws URISyntaxException {
        String uri = "http://dataservice.accuweather.com/locations/v1/cities/search";
        return String.format("%s?apikey=%s&q=%s",uri, API_KEY, location);
    }
}

