package com.ashr.weather.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ashr.weather.activities.R;
import com.ashr.weather.models.Datum;
import com.ashr.weather.models.Forecast;
import com.github.pwittchen.weathericonview.WeatherIconView;

import java.util.List;
import java.util.Locale;

import static com.ashr.weather.utilities.AndroidHelper.getStringIdentifier;
import static com.ashr.weather.utilities.DateTimeHelper.convertEpochToString;

public class GenericViewMain extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (view == null) this.view = inflater.inflate(R.layout.genericview_main, container, false);

        populateWeatherData();

        return view;
    }

    public static GenericViewMain newInstance(Forecast weatherData) {
        Bundle args = createBundle(weatherData);
        GenericViewMain fragment = new GenericViewMain();
        fragment.setArguments(args);
        return fragment;
    }

    private static Bundle createBundle(Forecast weatherData) {
        Bundle bundle = new Bundle();

        String iconName = "wi_forecast_io_" + weatherData.getCurrently().getIcon().replace("-", "_");
        Long curTemp = Math.round(weatherData.getCurrently().getTemperature());
        String curTempFull = String.format(Locale.ENGLISH, " %s°", curTemp);
        Long feelTemp = Math.round(weatherData.getCurrently().getApparentTemperature());
        String feelTempFull = String.format(Locale.ENGLISH, "%s°", feelTemp);
        String curStatus = String.format(Locale.ENGLISH, "%s", weatherData.getCurrently().getSummary());
   /*     double precipIntensity = weatherData.getCurrently().getPrecipIntensity();
        if(precipIntensity != 0) {
            String percipType = String.format(Locale.ENGLISH, "%s", weatherData.getDaily().getData().);
        }
        else
        {

        }*/
        String curHumidity = String.format(Locale.ENGLISH, "%s", weatherData.getCurrently().getHumidity());
        String curPressure = String.format(Locale.ENGLISH, "%s", weatherData.getCurrently().getPressure());
        String curWind = String.format(Locale.ENGLISH, "%s", weatherData.getCurrently().getWindSpeed());


        bundle.putString("iconName", iconName);
        bundle.putString("curTemp", curTempFull);
        bundle.putString("feelTemp", feelTempFull);
        bundle.putString("curStatus", curStatus);
        bundle.putString("curHumidity", curHumidity);
        bundle.putString("curPressure", curPressure);
        bundle.putString("curWind", curWind);


        return bundle;
    }

    private void populateWeatherData() {
        Bundle args = getArguments();

        View view1 = view.findViewById(R.id.main_layout);
        TextView loc = view1.findViewById(R.id.c_location);
        WeatherIconView icon = view1.findViewById(R.id.c_weathericon);
        TextView temp = view1.findViewById(R.id.c_temp);
        TextView feels = view1.findViewById(R.id.c_feels);
        TextView status = view1.findViewById(R.id.c_status);

        View view2 = view.findViewById(R.id.c_details_layout);
        TextView humidity = view2.findViewById(R.id.c_h)


        String iconName = args.getString("iconName");
        String weatherIcon = getActivity().getString(getStringIdentifier(getActivity(), iconName));
        String curTemp = args.getString("curTemp");
        String feelTemp = args.getString("feelTemp");
        String curStatus = args.getString("curStatus");

        icon.setIconResource(weatherIcon);
        temp.setText(curTemp);
        feels.setText("Feels Like"+" "+feelTemp);
        status.setText(curStatus);

    }
}
