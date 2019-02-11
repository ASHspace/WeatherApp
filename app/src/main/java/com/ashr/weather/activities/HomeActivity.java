package com.ashr.weather.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.ashr.weather.adapters.DailyDataAdapter;
import com.ashr.weather.adapters.ViewPagerAdapter;
import com.ashr.weather.fragments.ForecastDailyFragment;
import com.ashr.weather.fragments.ForecastMasterFragment;


import java.util.ArrayList;

import static com.ashr.weather.utilities.FragmentHelper.pushToFragmentManager;
import static com.ashr.weather.utilities.FragmentSupportHelper.pushToSupportFragmentManager;


/**
 *  This is the main activity that is called by default by our app. It's only function is to act as a content frame for our fragment manager.
 */
public class HomeActivity extends AppCompatActivity {
    String check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This app doesn't use the action bar, so it is being hidden.
       // getSupportActionBar().hide();

        // Setup the content view.
        setContentView(R.layout.activity_home);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new ForecastDailyFragment());
        fragments.add(new ForecastDailyFragment());

        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);

        tabLayout.setupWithViewPager(viewPager, true);
        tabLayout.getTabAt(0).setText("Today");
        tabLayout.getTabAt(1).setText("Weekly");


        // Since this is just a blank activity, we must push ForecastMasterFragment onto the content_frame.
        pushToFragmentManager(getFragmentManager(), R.id.content_frame, new ForecastMasterFragment(), false);

        pushToSupportFragmentManager(getSupportFragmentManager(), R.id.viewpager, new ForecastDailyFragment(), false);

    }
}
