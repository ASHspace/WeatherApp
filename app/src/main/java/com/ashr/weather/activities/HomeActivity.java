package com.ashr.weather.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.ashr.weather.adapters.DailyFragmentAdapter;
import com.ashr.weather.adapters.ViewPagerAdapter;
import com.ashr.weather.adapters.WeeklyFragmentAdapter;
import com.ashr.weather.fragments.AddCityDialogFragment;
import com.ashr.weather.fragments.DailyFragment;
import com.ashr.weather.fragments.ExampleFragment;
import com.ashr.weather.fragments.MainFragment;
import com.ashr.weather.fragments.WeeklyFragment;
import com.ashr.weather.models.Forecast;
import com.ashr.weather.models.WeatherLocation;
import com.ashr.weather.services.RandomKey;
import com.ashr.weather.services.WeatherApiUtils;
import com.ashr.weather.services.WeatherApiUtilsDaily;


import java.util.ArrayList;

import retrofit2.Response;

import static com.ashr.weather.utilities.FragmentHelper.pushToFragmentManager;


/**
 *  This is the main activity that is called by default by our app. It's only function is to act as a content frame for our fragment manager.
 */
public class HomeActivity extends AppCompatActivity implements WeatherApiUtils.MyCustomInterface {
    String check;
    Fragment mFragment;
    public static Response<Forecast> myStrings;
    DataLoadedListener mDataLoadedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This app doesn't use the action bar, so it is being hidden.
       // getSupportActionBar().hide();

        // Setup the content view.
        setContentView(R.layout.activity_home);


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        mFragment = new ExampleFragment();
        mDataLoadedListener = (DataLoadedListener) mFragment;

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(mFragment);
        fragments.add(new WeeklyFragment());

        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);

        tabLayout.setupWithViewPager(viewPager, true);
        tabLayout.getTabAt(0).setText("24 Hours");
        tabLayout.getTabAt(1).setText("Weekly");

        pushToFragmentManager(getFragmentManager(), R.id.content_frame, new MainFragment(), false);

        initializeWeatherData();

        // notify attached fragment
        mDataLoadedListener.onDataLoaded(myStrings);


        // Since this is just a blank activity, we must push ForecastMasterFragment onto the content_frame.
        //pushToFragmentManager(getFragmentManager(), R.id.content_frame, new ForecastMasterFragment(), false);

        //pushToSupportFragmentManager(getSupportFragmentManager(), R.id.viewpager, new WeeklyFragment_del(), false);

    }

    private void initializeWeatherData() {
        DailyFragmentAdapter dailyFragmentAdapter =  new DailyFragmentAdapter(null, this);
        WeeklyFragmentAdapter weeklyFragmentAdapter = new WeeklyFragmentAdapter(null, this);

        WeatherLocation location = new WeatherLocation(this);

        RandomKey randomKey = new RandomKey();
        String api_key = randomKey.myrandomKey();

        // Make sure the user has put in a location.
        if (location.getName() != null) {
            // Fetch the current forecast, which updates current conditions and weekly forecast.
            WeatherApiUtils.getWeatherData(location.getLatitudeLongitude(), api_key);

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

    public interface DataLoadedListener {
        public void onDataLoaded(Response<Forecast> res);
    }


}
