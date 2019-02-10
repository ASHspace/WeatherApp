package com.ashr.weather.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashr.weather.activities.R;
import com.ashr.weather.models.Datum;

import java.util.List;
import java.util.Locale;

import static com.ashr.weather.utilities.AndroidHelper.getStringIdentifier;
import static com.ashr.weather.utilities.DateTimeHelper.convertEpochToString;


public class DailyDataAdapter extends RecyclerView.Adapter<ForecastMasterViewHolder> {
    @Nullable
    public static List<Datum> weeklyForecast;
    private LayoutInflater inflater;
    private Context context;

    public DailyDataAdapter(@Nullable List<Datum> weeklyForecast, Context context) {
        this.weeklyForecast = weeklyForecast;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public ForecastMasterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.week_view, parent, false);
        return new ForecastMasterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForecastMasterViewHolder holder, int position) {
        Datum day = weeklyForecast.get(position);

        String dayName = convertEpochToString(day.getTime(), "EEE", "GMT-6:00").toUpperCase();
        String dayNameFormatted = String.format(Locale.ENGLISH, "%s", dayName);

        // Setup high and low temps.
        Long lowTemp = Math.round(day.getTemperatureMin());
        String lowTempFormatted = String.format(Locale.ENGLISH, "%s°", lowTemp);

        Long highTemp = Math.round(day.getTemperatureMax());
        String highTempFormatted = String.format(Locale.ENGLISH, "%s°", highTemp);

        String iconString = "wi_forecast_io_" + day.getIcon().replace("-", "_");
        String weatherIconResource = context.getString(getStringIdentifier(context, iconString));

        holder.day.setText(dayNameFormatted);
        holder.low.setText(lowTempFormatted);
        holder.high.setText(highTempFormatted);
        holder.icon.setIconResource(weatherIconResource);
    }

    @Override
    public int getItemCount() {
        return (weeklyForecast != null) ? weeklyForecast.size() : 0;
    }

    public void updateForecastData(List<Datum> weeklyForecast) {
        this.weeklyForecast = weeklyForecast;
        updateView();
    }

    public void updateView() {
        notifyDataSetChanged();
    }


}
