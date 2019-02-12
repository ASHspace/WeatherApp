package com.ashr.weather.services;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.ashr.weather.models.Forecast;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherApiUtils2 {
    private static final String BASE_URL = "https://api.darksky.net/forecast/";


    /**
     * Uses retrofit to call the Darksky api using the provided api key.
     * @param apiKey Needed to access the Darksky api.
     * @return WeatherService
     */
    private static WeatherService getWeatherService(String apiKey) {
        return RetrofitClient.getClient(BASE_URL+apiKey+"/").create(WeatherService.class);
    }


    public static Bundle getWeatherData(Location location, String apiKey) {

        WeatherService api = WeatherApiUtils2.getWeatherService(apiKey);

        Map<String, String> unitData = new HashMap<>();
        unitData.put("units", "si");

        api.getWeather(location.getLatitude(), location.getLongitude(),unitData).enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                if (response.isSuccessful()) {
                    Log.d("Raw", "RetroFit2.0 :RetroGetLogin: " + response.body().toString());
                    //List<Datum> dailyData = response.body().getDaily().getData();

                    // Update the forecast data, but return a new list that does not have today in it.
                    //adapter.updateForecastData(dailyData.subList(1, dailyData.size()));

                    // Update the current conditions views.
                    //fragment.updateCurrentConditions(response.body());
                    return;
                } else {
                    Log.e("rest error", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                Log.d("Weather API", "error loading from API");
                Log.d("Weather API", t.getMessage());
            }
        });

        return null;
    }
}