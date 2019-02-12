package com.ashr.weather.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ashr.weather.activities.R;
import com.github.pwittchen.weathericonview.WeatherIconView;


public class WeeklyFragmentViewHolder extends RecyclerView.ViewHolder {
    TextView day;
    TextView high;
    TextView low;
    WeatherIconView icon;

    public WeeklyFragmentViewHolder(View itemView) {
        super(itemView);

        day = (TextView) itemView.findViewById(R.id.w_day);
        low = (TextView) itemView.findViewById(R.id.w_low);
        high = (TextView) itemView.findViewById(R.id.w_high);
        icon = (WeatherIconView) itemView.findViewById(R.id.w_icon);
    }

}
