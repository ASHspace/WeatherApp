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
import com.ashr.weather.models.Datum_;
import com.ashr.weather.models.Forecast;
import com.github.pwittchen.weathericonview.WeatherIconView;

import java.util.List;
import java.util.Locale;

import static com.ashr.weather.utilities.AndroidHelper.getStringIdentifier;
import static com.ashr.weather.utilities.DateTimeHelper.convertEpochToString;

public class GenericViewDaily extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if(view == null) this.view = inflater.inflate(R.layout.generic_view2, container, false);

        populateWeatherData();

        return view;
    }

    public static GenericViewDaily newInstance(Forecast weatherData) {
        Bundle args = createBundle(weatherData);
        GenericViewDaily fragment = new GenericViewDaily();
        fragment.setArguments(args);
        return fragment;
    }

    private static Bundle createBundle(Forecast weatherData) {
        Bundle bundle = new Bundle();
        List<Datum_> dailyData = weatherData.getHourly().getData();

        int i=0;


        for(Datum_ today: dailyData){
            String daTime = convertEpochToString(today.getTime(), "h a", "GMT-6:00");
            String dayTimeFull = String.format(Locale.ENGLISH, "%s", daTime);
            String dayName = convertEpochToString(today.getTime(), "E", "GMT-6:00");
            String dayNameFull = String.format(Locale.ENGLISH, "%s", dayName);
            String iconName = "wi_forecast_io_" + today.getIcon().replace("-", "_");
            String summary = today.getSummary();
            String summaryFull = String.format(Locale.ENGLISH, "%s°", summary);
            Long temp = Math.round(today.getTemperature());
            String tempFull = String.format(Locale.ENGLISH, "%s°", temp);





            bundle.putString("dayTime" + i , dayTimeFull);
            bundle.putString("dayName" + i , dayNameFull);
            bundle.putString("iconName" + i , iconName);
            bundle.putString("summary" + i , summaryFull);
            bundle.putString("temp" + i , tempFull);

            i++;
        }



        return bundle;
    }

    private void populateWeatherData() {
        Bundle args = getArguments();

        for(int count = 0; count<7; count++) {

            String iView = "day_"+(count+1);
            int resName = getResources().getIdentifier(iView, "id", getActivity().getPackageName());
            View view1 = view.findViewById(resName);

            TextView time = view1.findViewById(R.id.h_time);
            TextView day = view1.findViewById(R.id.h_day);
            WeatherIconView icon = view1.findViewById(R.id.h_icon);
            TextView summary = view1.findViewById(R.id.h_type);
            TextView temp = view1.findViewById(R.id.h_temp);


            String dayTime= args.getString("dayTime"+count);
            String dayName = args.getString("dayName"+count);
            String iconName = args.getString("iconName"+count);
            String weatherIcon = getActivity().getString(getStringIdentifier(getActivity(), iconName));
            String summaryText = args.getString("summary"+count);
            String tempText = args.getString("temp"+count);

            time.setText(dayTime);
            day.setText(dayName);
            icon.setIconResource(weatherIcon);
            summary.setText(summaryText);
            temp.setText(tempText);
        }

    }
}
