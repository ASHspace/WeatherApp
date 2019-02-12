package com.ashr.weather.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashr.weather.activities.R;
import com.ashr.weather.models.Datum;
import com.ashr.weather.models.Datum_;

import java.util.List;
import java.util.Locale;

import static com.ashr.weather.utilities.AndroidHelper.getStringIdentifier;
import static com.ashr.weather.utilities.DateTimeHelper.convertEpochToString;


public class DailyFragmentAdapter extends RecyclerView.Adapter<DailyFragmentViewHolder> {
    @Nullable
    public static List<Datum_> dailyWeatherData;
    private LayoutInflater inflater;
    private Context context;

    public DailyFragmentAdapter(@Nullable List<Datum_> dailyWeatherData, Context context) {
        this.dailyWeatherData = dailyWeatherData;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public DailyFragmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.daily_view, parent, false);
        return new DailyFragmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DailyFragmentViewHolder holder, int position) {
        Datum_ day = dailyWeatherData.get(position);

        String dayName = convertEpochToString(day.getTime(), "h a", "GMT-6:00");
        String dayNameFormatted = String.format(Locale.ENGLISH, "%s", dayName);

        String hourDate = convertEpochToString(day.getTime(), "EEE", "GMT-6:00");
        String hourDateFormatted = String.format(Locale.ENGLISH, "%s", hourDate);

        String type = day.getSummary();

        // Setup high and low temps.
        Long lowTemp = Math.round(day.getTemperature());
        String lowTempFormatted = String.format(Locale.ENGLISH, "%s°", lowTemp);

        Long highTemp = Math.round(day.getApparentTemperature());
        String highTempFormatted = String.format(Locale.ENGLISH, "%s°", highTemp);

        String iconString = "wi_forecast_io_" + day.getIcon().replace("-", "_");
        String weatherIconResource = context.getString(getStringIdentifier(context, iconString));

        holder.hour.setText(dayNameFormatted);
        holder.date.setText(hourDateFormatted);
       // holder.type.setText(type);
        holder.high.setText(lowTempFormatted+"/"+highTempFormatted);
        //holder.high.setText(highTempFormatted);
        holder.icon.setIconResource(weatherIconResource);
    }

    @Override
    public int getItemCount() {
        return (dailyWeatherData != null) ? dailyWeatherData.size() : 0;
    }

    public void updateForecastData(List<Datum_> dailyWeatherData) {
        this.dailyWeatherData = dailyWeatherData;
        updateView();
    }

    public void updateView() {
        notifyDataSetChanged();
    }
}
