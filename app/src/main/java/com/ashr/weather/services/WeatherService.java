package com.ashr.weather.services;

import com.ashr.weather.models.Forecast;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * This is an interface necessary for Retrofit to properly work with the Darksky API.
 */
public interface WeatherService {
    @GET("{latitude},{longitude}")
    Call<Forecast> getWeather(
            @Path("latitude") Double latitude,
            @Path("longitude") Double longitude,
            @QueryMap Map<String, String> units
    );
}