package com.example.mushroomapi.weather;

import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class AccuWeatherUriBuilderTest {

    @Test
    void checkIfCreatedLocationRequestUriMatchExpectedUri() throws URISyntaxException {
        //given
        String location = "Kraków";
        String expected = "http://dataservice.accuweather.com/locations/v1/cities/search?apikey=OJT7tYGziR9z19njtdykrrQr2FZpSiKi&q=Kraków";
        //when
        AccuWeatherUriBuilder accuWeatherUriBuilder = new AccuWeatherUriBuilder();
        String result = accuWeatherUriBuilder.createLocationRequestUri(location);
        //then
        assertEquals(expected, result);
    }
}