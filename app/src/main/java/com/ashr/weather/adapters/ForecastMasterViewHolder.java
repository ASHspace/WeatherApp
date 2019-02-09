package com.ashr.weather.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ashr.weather.activities.R;
import com.github.pwittchen.weathericonview.WeatherIconView;


public class ForecastMasterViewHolder extends RecyclerView.ViewHolder {
    TextView day;
    TextView low;
    TextView high;
    WeatherIconView icon;

    public ForecastMasterViewHolder(View itemView) {
        super(itemView);

        day = (TextView) itemView.findViewById(R.id.tv_weekly_forecast_day);
        low = (TextView) itemView.findViewById(R.id.tv_weekly_forecast_low);
        high = (TextView) itemView.findViewById(R.id.tv_weekly_forecast_high);
        icon = (WeatherIconView) itemView.findViewById(R.id.weather_icon_item);
    }
}
