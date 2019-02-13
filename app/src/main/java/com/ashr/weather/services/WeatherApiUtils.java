package com.ashr.weather.services;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.ashr.weather.activities.HomeActivity;
import com.ashr.weather.activities.MainActivity;
import com.ashr.weather.adapters.DailyFragmentAdapter;
import com.ashr.weather.adapters.ForecastMasterAdapter;
import com.ashr.weather.adapters.WeeklyFragmentAdapter;
import com.ashr.weather.fragments.DailyFragment;
import com.ashr.weather.fragments.ForecastMasterFragment;
import com.ashr.weather.fragments.WeeklyFragment;
import com.ashr.weather.models.Datum;
import com.ashr.weather.models.Datum_;
import com.ashr.weather.models.Forecast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherApiUtils {
    private final static String BASE_URL = "https://api.darksky.net/forecast/";
    MyCustomInterface myCustomInterface;

    /**
     * Uses retrofit to call the Darksky api using the provided api key.
     * @param apiKey Needed to access the Darksky api.
     * @return WeatherService
     */
    private static WeatherService getWeatherService(String apiKey) {
        return RetrofitClient.getClient(BASE_URL+apiKey+"/").create(WeatherService.class);
    }

    public WeatherApiUtils(MyCustomInterface myCustomInterface) {
        this.myCustomInterface = myCustomInterface;
    }


    public void getWeatherData(Location location,
                                      String apiKey) {

        WeatherService api = WeatherApiUtils.getWeatherService(apiKey);


        Map<String, String> data = new HashMap<>();
        data.put("units", "si");

        Log.i("API", api.getWeather(location.getLatitude(), location.getLongitude(), data).request().url().toString());

        api.getWeather(location.getLatitude(), location.getLongitude(),data).enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                if (response.isSuccessful()) {
                    List<Datum_> dailyData = response.body().getHourly().getData();
                    List<Datum> weeklyData = response.body().getDaily().getData();

                    passData(response);
                    // Update the forecast data, but return a new list that does not have today in it.
                   /* dailyFragmentAdapter.updateForecastData(dailyData.subList(1, 7));

                    // Update the current conditions views.
                    dailyFragment.updateCurrentConditions(response.body());


                    weeklyFragmentAdapter.updateForecastData(weeklyData);

                    // Update the current conditions views.
                    weeklyFragment.updateCurrentConditions(response.body());*/

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
    }

    public void passData(Response<Forecast> res){
        myCustomInterface.sendData(res);
    }
    public interface MyCustomInterface{
        void sendData(Response<Forecast> res2);
    }
}