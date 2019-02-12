package com.ashr.weather.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ashr.weather.activities.R;
import com.ashr.weather.adapters.CustomLinearLayoutManager;
import com.ashr.weather.adapters.DailyFragmentAdapter;
import com.ashr.weather.adapters.ForecastMasterAdapter;
import com.ashr.weather.models.Forecast;
import com.ashr.weather.models.WeatherLocation;
import com.ashr.weather.services.RandomKey;
import com.ashr.weather.services.WeatherApiUtils2;
import com.ashr.weather.services.WeatherApiUtilsDaily;
import com.ashr.weather.utilities.ItemClickSupport;


/**
 * This fragment shows the user the current conditions of their entered location.
 * It also provides a RecyclerView that shows the following weeks weather forecast.
 */
public class DailyFragment extends Fragment implements View.OnClickListener {
    public DailyFragmentAdapter adapter;
    private RecyclerView recyclerView;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Only inflate our view and do the setup functions if the view is null. This prevents pop in of data and unnecessary API calls.
        if(view == null) {
            this.view = inflater.inflate(R.layout.activity_placeholder, container, false);

            // Setup the adapter so that it can be modified later asynchronously.
            this.adapter = new DailyFragmentAdapter(null, view.getContext());

            // Fetch the location data and setup all weather data on this fragment.
            //initializeWeatherData();



            // Set up the recyclerview.
            setupRecyclerView();


            // Make the settings button clickable.
           // setupSettingsButton();
        }
        return view;
    }


    @Override
    public void onClick(View v) {
        // If the user taps the settings button. Send them to that fragment.
        if(v.getId() == R.id.button_settings) {
            //FragmentSupportHelper.pushToSupportFragmentManager(getFragmentManager(), R.id.content_frame, new SettingsFragment(), true);
        }
    }

    /**
     * Makes the settings button clickable by adding a click listener.
     */
    private void setupSettingsButton() {
        View settingsButton = view.findViewById(R.id.button_settings);
        settingsButton.setOnClickListener(this);
    }


    /**
     * If the user has provided a location, this function calls the Darksky API and populates all data accordingly.
     * Otherwise, a DialogFragment is shown that allows them to do so.
     */
    private void initializeWeatherData() {
        WeatherLocation location = new WeatherLocation(getActivity());

        RandomKey randomKey = new RandomKey();
        String api_key = randomKey.myrandomKey();

        // Make sure the user has put in a location.
        if (location.getName() != null) {
            // Fetch the current forecast, which updates current conditions and weekly forecast.
            WeatherApiUtilsDaily.getWeatherData(location.getLatitudeLongitude(), adapter, api_key, this);

            // Set the text on the location label.
            //TextView locationLabel = (TextView) view.findViewById(R.id.text_location_name);
            //locationLabel.setText(location.getName());

            // If they haven't, ask them to put in a location.
        } else {
            AddCityDialogFragment addCityDialogFragment = new AddCityDialogFragment().newInstance();

            if (!addCityDialogFragment.isActive()) {
                //addCityDialogFragment.show(getSupportFragmentManager(), "fragment_add_city");
            }
        }
    }

    /**
     * Fetches and configures the RecyclerView to be displayed.
     */
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

    /**
     * This populates all the current conditions on the fragment.
     * @param weatherData This is used to populate all of our labels.
     */
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
}