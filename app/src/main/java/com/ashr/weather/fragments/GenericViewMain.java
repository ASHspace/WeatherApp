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

        if(view == null) this.view = inflater.inflate(R.layout.genericview_main, container, false);

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
        List<Datum> dailyData = weatherData.getDaily().getData();
        dailyData = dailyData.subList(1, 8);
        int i=0;


        for(Datum today: dailyData){
         /*   String dayName = convertEpochToString(today.getTime(), "E", "GMT-6:00");
            String dayNameFull = String.format(Locale.ENGLISH, "%s", dayName);
            String iconName = "wi_forecast_io_" + today.getIcon().replace("-", "_");
            Long highTemp = Math.round(today.getTemperatureMax());
            String highTempFull = String.format(Locale.ENGLISH, "%s°", highTemp);
            Long lowTemp = Math.round(today.getTemperatureMin());
            String lowTempFull = String.format(Locale.ENGLISH, "%s°", lowTemp);





            bundle.putString("dayName" + i , dayNameFull);
            bundle.putString("iconName" + i , iconName);
            bundle.putString("highTemp" + i , highTempFull);
            bundle.putString("lowTemp" + i , lowTempFull);*/

            i++;
        }



        return bundle;
    }

    private void populateWeatherData() {
        Bundle args = getArguments();

   /*     for(int count = 0; count<7; count++) {

            String iView = "week_"+(count+1);
            int resName = getResources().getIdentifier(iView, "id", getActivity().getPackageName());
            View view1 = view.findViewById(resName);
            TextView day = view1.findViewById(R.id.w_day);
            WeatherIconView icon = view1.findViewById(R.id.w_icon);
            TextView high = view1.findViewById(R.id.w_high);
            TextView low = view1.findViewById(R.id.w_low);


            String dayName = args.getString("dayName"+count);
            String iconName = args.getString("iconName"+count);
            String weatherIcon = getActivity().getString(getStringIdentifier(getActivity(), iconName));
            String highTemp = args.getString("highTemp"+count);
            String lowTemp = args.getString("lowTemp"+count);

            day.setText(dayName);
            icon.setIconResource(weatherIcon);
            high.setText(highTemp);
            low.setText(lowTemp);
        }
*/
    }
}
