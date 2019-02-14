package com.ashr.weather.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ashr.weather.adapters.DailyFragmentAdapter;
import com.ashr.weather.adapters.MainFragmentAdapter;
import com.ashr.weather.adapters.ViewPagerAdapter;
import com.ashr.weather.adapters.WeeklyFragmentAdapter;
import com.ashr.weather.fragments.AddCityDialogFragment;
import com.ashr.weather.fragments.DailyFragment;
import com.ashr.weather.fragments.GenericView;
import com.ashr.weather.fragments.GenericViewDaily;
import com.ashr.weather.fragments.GenericViewMain;
import com.ashr.weather.fragments.MainFragment;
import com.ashr.weather.fragments.WeeklyFragment;
import com.ashr.weather.models.Datum;
import com.ashr.weather.models.Datum_;
import com.ashr.weather.models.Datum__;
import com.ashr.weather.models.Forecast;
import com.ashr.weather.models.WeatherLocation;
import com.ashr.weather.services.RandomKey;
import com.ashr.weather.services.WeatherApiUtils;
import com.ashr.weather.utilities.FragmentHelper;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

import static com.ashr.weather.utilities.FragmentHelper.pushToFragmentManager;


/**
 *  This is the main activity that is called by default by our app. It's only function is to act as a content frame for our fragment manager.
 */
public class HomeActivity extends AppCompatActivity implements WeatherApiUtils.MyCustomInterface {
    String check;
    Fragment mFragment;
    public Response<Forecast> myStrings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This app doesn't use the action bar, so it is being hidden.
       // getSupportActionBar().hide();

        // Setup the content view.
        setContentView(R.layout.activity_home);


        //ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);


        /*ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new DailyFragment());
        fragments.add(new WeeklyFragment());

        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);

        tabLayout.setupWithViewPager(viewPager, true);
        tabLayout.getTabAt(0).setText("24 Hours");
        tabLayout.getTabAt(1).setText("Weekly");

        pushToFragmentManager(getFragmentManager(), R.id.content_frame, new MainFragment(), false);
*/
        initializeWeatherData();






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
            WeatherApiUtils weatherApiUtils = new WeatherApiUtils(this);
            weatherApiUtils.getWeatherData(location.getLatitudeLongitude(), api_key);

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

    @Override
    public void sendData(Response<Forecast> res2) {
        this.myStrings = res2;
        //List<Datum__> currentData = res2.body().getMinutely().getData();
        List<Datum_> dailyData = res2.body().getHourly().getData();
        List<Datum> weeklyData = res2.body().getDaily().getData();
        DailyFragment dailyFragment = new DailyFragment();
        WeeklyFragment weeklyFragment = new WeeklyFragment();
        GenericView genericView = new GenericView().newInstance(res2.body());
        GenericViewMain genericViewDailyView = new GenericViewMain().newInstance(res2.body());



        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(dailyFragment);
        fragments.add(genericView);

        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);

        tabLayout.setupWithViewPager(viewPager, true);
        tabLayout.getTabAt(0).setText("Hourly");
        tabLayout.getTabAt(1).setText("Weekly");


       // MainFragmentAdapter mainFragmentAdapter = new MainFragmentAdapter(currentData, getApplicationContext());
        DailyFragmentAdapter dailyFragmentAdapter= new DailyFragmentAdapter(dailyData, getApplicationContext());
        //WeeklyFragmentAdapter weeklyFragmentAdapter = new WeeklyFragmentAdapter(weeklyData, this);

        // Update the forecast data, but return a new list that does not have today in it.
        dailyFragmentAdapter.updateForecastData(dailyData.subList(1, 25));
        //weeklyFragmentAdapter.updateForecastData(weeklyData.subList(1, weeklyData.size()));

        // Update the current conditions views.
        dailyFragment.updateCurrentConditions(res2.body());

        //weeklyFragment.updateCurrentConditions(res2.body());


        FragmentHelper.pushToFragmentManager(getSupportFragmentManager(), R.id.content_frame, genericViewDailyView, false);

        //mainFragment.updateCurrentConditions(res2.body());



       //pushToFragmentManager(getFragmentManager(), R.id.content_frame, new MainFragment(), false);



    }


}
