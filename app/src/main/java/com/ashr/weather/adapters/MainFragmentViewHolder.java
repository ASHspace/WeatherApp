package com.ashr.weather.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ashr.weather.activities.R;
import com.github.pwittchen.weathericonview.WeatherIconView;


public class MainFragmentViewHolder extends RecyclerView.ViewHolder {
    TextView temp;
    TextView high;
    TextView low;
    WeatherIconView icon;

    public MainFragmentViewHolder(View itemView) {
        super(itemView);

        temp = (TextView) itemView.findViewById(R.id.c_temp);
//        low = (TextView) itemView.findViewById(R.id.w_low);
  //      high = (TextView) itemView.findViewById(R.id.w_high);
        icon = (WeatherIconView) itemView.findViewById(R.id.w_icon);
    }

}
