package com.ashr.weather.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashr.weather.activities.R;
import com.ashr.weather.models.Datum;
import com.ashr.weather.models.Datum__;

import java.util.List;
import java.util.Locale;

import static com.ashr.weather.utilities.AndroidHelper.getStringIdentifier;
import static com.ashr.weather.utilities.DateTimeHelper.convertEpochToString;


public class MainFragmentAdapter extends RecyclerView.Adapter<MainFragmentViewHolder> {
    @Nullable
    public static List<Datum__> currentForecast;
    private LayoutInflater inflater;
    private Context context;

    public MainFragmentAdapter(@Nullable List<Datum__> currentForecast, Context context) {
        this.currentForecast = currentForecast;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public MainFragmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.week_view, parent, false);
        return new MainFragmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainFragmentViewHolder holder, int position) {
        Datum__ day = currentForecast.get(position);

        String dayName = convertEpochToString(day.getTime(), "EEE", "GMT-6:00");
        String dayNameFormatted = String.format(Locale.ENGLISH, "%s", dayName);

        // Setup high and low temps.
        Long lowTemp = Math.round(day.getPrecipIntensity());
        String lowTempFormatted = String.format(Locale.ENGLISH, "%s°", lowTemp);

        Long highTemp = Math.round(day.getPrecipProbability());
        String highTempFormatted = String.format(Locale.ENGLISH, "%s°", highTemp);
/*

        String iconString = "wi_forecast_io_" + day.getIcon().replace("-", "_");
        String weatherIconResource = context.getString(getStringIdentifier(context, iconString));
*/

        holder.temp.setText(dayNameFormatted);
        holder.low.setText(lowTempFormatted);
        holder.high.setText(highTempFormatted);
        //holder.icon.setIconResource(weatherIconResource);
    }

    @Override
    public int getItemCount() {
        return (currentForecast != null) ? currentForecast.size() : 0;
    }

    public void updateForecastData(List<Datum__> currentForecast) {
        this.currentForecast = currentForecast;
        updateView();
    }

    public void updateView() {
        notifyDataSetChanged();
    }
}
