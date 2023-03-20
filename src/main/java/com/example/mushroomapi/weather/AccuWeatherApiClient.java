package com.example.mushroomapi.weather;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@AllArgsConstructor // (no need or autowired with this)
@Component
public class AccuWeatherApiClient {
    @Autowired
    private final AccuWeatherUriBuilder accuWeatherUriBuilder;

    public AccuWeatherLocationDto getLocationDto(String location) throws URISyntaxException, IOException, InterruptedException {

        String uriAddress = accuWeatherUriBuilder.createLocationRequestUri(location);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(uriAddress))
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newBuilder()
                .build();

        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        return null;
    }
}
