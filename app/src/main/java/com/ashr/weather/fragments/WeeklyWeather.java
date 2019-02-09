package com.ashr.weather.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashr.weather.activities.R;
import com.ashr.weather.adapters.ForecastMasterAdapter;
import com.ashr.weather.utilities.FragmentHelper;
import com.ashr.weather.utilities.ItemClickSupport;


public class WeeklyWeather extends Fragment {
    public ForecastMasterAdapter adapter;
    private RecyclerView recyclerView;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_placeholder, container, false);
        setupRecyclerView();
        return view;
    }


    private void setupRecyclerView() {
        // Get the recyclerview so that we can set it up.
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_week_forecast);

        this.adapter = new ForecastMasterAdapter(null, view.getContext());

        // Set the adapter for the recyclerview. In this case, we are using null for our weather data because it will be fetched asynchronously later on.
        recyclerView.setAdapter(adapter);

        // Use this ItemClickSupport found at the following url to setup click support.
        // https://gist.github.com/nesquena/231e356f372f214c4fe6


        // Setup the layout as Linear.
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
    }
}
