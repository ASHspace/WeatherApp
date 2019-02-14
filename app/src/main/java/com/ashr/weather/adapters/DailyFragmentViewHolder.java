package com.ashr.weather.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ashr.weather.activities.R;
import com.github.pwittchen.weathericonview.WeatherIconView;


public class DailyFragmentViewHolder extends RecyclerView.ViewHolder {
    TextView hour;
    TextView date;
    TextView type;
    TextView low;
    TextView high;
    WeatherIconView icon;

    public DailyFragmentViewHolder(View itemView) {
        super(itemView);

        hour = (TextView) itemView.findViewById(R.id.tv_weekly_forecast_day);
        date = (TextView) itemView.findViewById(R.id.hourly_date);
        high = (TextView) itemView.findViewById(R.id.tv_weekly_forecast_high);
        icon = (WeatherIconView) itemView.findViewById(R.id.weather_icon_item);
    }

}
