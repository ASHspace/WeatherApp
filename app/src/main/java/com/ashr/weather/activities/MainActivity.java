package com.ashr.weather.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ashr.weather.adapters.ViewPagerAdapter;
import com.ashr.weather.fragments.ForecastMasterFragment;


import java.util.ArrayList;

import static com.ashr.weather.utilities.FragmentHelper.pushToFragmentManager;

public class MainActivity extends AppCompatActivity {
    private ViewPager mMyViewPager;
    private TabLayout mTabLayout;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.action_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mTabLayout = findViewById(R.id.tablayout);
        mMyViewPager = findViewById(R.id.viewpager);



        //addToFragmentManager(getSupportFragmentManager(), R.id.content_frame, new TodayWeather(), false);

       // pushToFragmentManager(getFragmentManager(), R.id.content_frame, new ForecastMasterFragment(), false);
    }

}
