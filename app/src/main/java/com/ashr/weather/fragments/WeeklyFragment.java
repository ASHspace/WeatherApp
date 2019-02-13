package com.ashr.weather.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ashr.weather.activities.R;
import com.ashr.weather.adapters.DailyFragmentAdapter;
import com.ashr.weather.adapters.WeeklyFragmentAdapter;
import com.ashr.weather.models.Datum;
import com.ashr.weather.models.Forecast;
import com.ashr.weather.utilities.AndroidHelper;
import com.ashr.weather.utilities.DateTimeHelper;
import com.ashr.weather.utilities.ItemClickSupport;
import com.github.pwittchen.weathericonview.WeatherIconView;

import java.util.List;

/**
 * When a user selects a day from the RecyclerView weekly forecast on the ForecastMasterFragment, this Fragment is displayed.
 * The index (int selectedDay) is passed into the newInstance and then used to populate all data.
 */
public class WeeklyFragment extends Fragment implements View.OnClickListener{
    public WeeklyFragmentAdapter adapter;
    private RecyclerView recyclerView;
    private View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Only inflate our view and do the setup functions if the view is null. This prevents pop in of data and unnecessary API calls.
        if(view == null) {
            this.view = inflater.inflate(R.layout.activity_placeholder, container, false);

            // Setup the adapter so that it can be modified later asynchronously.
            this.adapter = new WeeklyFragmentAdapter(null, view.getContext());

            // Fetch the location data and setup all weather data on this fragment.
            //initializeWeatherData();



            // Set up the recyclerview.
            setupRecyclerView();


            // Make the settings button clickable.
            // setupSettingsButton();
        }
        return view;
    }

    /**
     * Pulls all arguments and then pouplates the text views with the correct data.
     */
    private void populateWeatherData() {

        // Get all of our arguments that were populated earlier.
        Bundle args = getArguments();

        /**
         * Setup all of our text views.
         */
        TextView dayName         = (TextView) view.findViewById(R.id.w_day);
        WeatherIconView weatherIcon = (WeatherIconView) view.findViewById(R.id.w_icon);
        TextView lowTemp            = (TextView) view.findViewById(R.id.w_low);
        TextView highTemp           = (TextView) view.findViewById(R.id.w_high);

        /**
         * Format all of our data correctly.
         */
        String dateString           = DateTimeHelper.convertEpochToString(args.getInt("time"), "h a", "GMT-6:00");
        String weatherIconResource  = getString(AndroidHelper.getStringIdentifier(getActivity(), args.getString("icon")));
        long lowTempLong            = Math.round(Double.valueOf(args.getString("low")));
        long highTempLong           = Math.round(Double.valueOf(args.getString("high")));


        /**
         * Populate data from the bundle into the text views.
         */
        dayName.setText(dateString);
        weatherIcon.setIconResource(weatherIconResource);
        lowTemp.setText(getString(R.string.weather_temperature, lowTempLong));
        highTemp.setText(getString(R.string.weather_temperature, highTempLong));
    }

    /**
     * A helper to create our bundle that is used within onCreateView.
     * @param forecast
     * @param selectedDay
     * @return Bundle
     */
    private static Bundle createBundle(List<Datum> forecast, int selectedDay) {
        // Create a bundle so that we can access it when the view is being created below.
        Bundle bundle = new Bundle();

        // Filter out the selected day from the forecast.
        Datum dailyForecast = forecast.get(selectedDay);

        /**
         * Build out our bundle with weather data.
         */
        bundle.putInt("time", dailyForecast.getTime());
        bundle.putString("icon", "wi_forecast_io_" + dailyForecast.getIcon().replace("-", "_"));
        bundle.putString("low", String.valueOf(dailyForecast.getTemperatureMin()));
        bundle.putString("high", String.valueOf(dailyForecast.getTemperatureMax()));

        return bundle;
    }

    public void updateCurrentConditions(Forecast weatherData) {
        // If the view doesn't exist, an error will occur because we are calling it below. Return to prevent this.
        if (view == null || !isAdded()) {
            return;
        }

        /**
         * Fetch all the text views to populate them below.
         */
        TextView currentTempLabel           = (TextView) view.findViewById(R.id.text_current_temp);
        TextView currentConditionLabel      = (TextView) view.findViewById(R.id.text_current_condition);
        TextView currentPrecipitationLabel  = (TextView) view.findViewById(R.id.text_current_precipitation);
        TextView currentWindLabel           = (TextView) view.findViewById(R.id.text_wind_speed);
        TextView todayHighTempLabel         = (TextView) view.findViewById(R.id.text_today_high);
        TextView todayLowTempLabel          = (TextView) view.findViewById(R.id.text_today_low);

        /**
         * Fetch all the data.
         */
        String summary      = weatherData.getCurrently().getSummary();
        long currentTemp    = Math.round(weatherData.getCurrently().getTemperature());
        long precipitation  = Math.round(weatherData.getCurrently().getPrecipProbability());
        long lowTemp        = Math.round(weatherData.getDaily().getData().get(0).getTemperatureMin());
        long highTemp       = Math.round(weatherData.getDaily().getData().get(0).getTemperatureMax());
        long windSpeed      = Math.round(weatherData.getCurrently().getWindSpeed());

        /**
         *  Populate all the text views.
         */


    }


    private void setupRecyclerView() {
        // Get the recyclerview so that we can set it up.
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_week_forecast);

        // Set the adapter for the recyclerview. In this case, we are using null for our weather data because it will be fetched asynchronously later on.
        recyclerView.setAdapter(adapter);

        // Use this ItemClickSupport found at the following url to setup click support.
        // https://gist.github.com/nesquena/231e356f372f214c4fe6
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        // When the user taps an item, a new instance of ForecastDetailFragment is provided and the position they selected is passed.
                        // FragmentSupportHelper.pushToSupportFragmentManager(getFragmentManager(), R.id.viewpager, new ForecastDetailFragment().newInstance(adapter.weeklyForecast, position), true);
                    }
                }
        );

        // Setup the layout as Linear.
        // CustomLinearLayoutManager layoutManager = new CustomLinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }




    @Override
    public void onClick(View view) {

    }
}
