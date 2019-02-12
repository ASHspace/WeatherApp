package com.ashr.weather.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ashr.weather.activities.HomeActivity;
import com.ashr.weather.activities.R;
import com.ashr.weather.models.Datum;
import com.ashr.weather.models.Forecast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * Created by nick on 21/05/18.
 */

public class ExampleFragment extends Fragment implements HomeActivity.DataLoadedListener {
    TextView tvStringList;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_example, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //tvStringList = view.findViewById(R.id.tvStringList);
    }

    @Override
    public void onDataLoaded(Response<Forecast> res) {
        Log.d("Raw", "RetroFit2.0 :RetroGetLogin: " + res.body().toString());
    }
}
