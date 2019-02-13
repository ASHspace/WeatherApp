package com.ashr.weather.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class GenericView extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if(view == null) this.view = inflater.inflate(R.layout.generic_view, container, false);

        populateWeatherData();

        return view;
    }

    public static GenericView newInstance(Forecast weatherData) {
        Bundle args = createBundle(weatherData);
        GenericView fragment = new GenericView();
        fragment.setArguments(args);
        return fragment;
    }

    private static Bundle createBundle(Forecast weatherData) {
        Bundle bundle = new Bundle();
        List<Datum> dailyData = weatherData.getDaily().getData();
        dailyData = dailyData.subList(1, 8);
        int i=0;


        for(Datum today: dailyData){
            String dayName = convertEpochToString(today.getTime(), "EEE", "GMT-6:00");
            String dayNameFormatted = String.format(Locale.ENGLISH, "%s", dayName);

            String iconString = "wi_forecast_io_" + today.getIcon().replace("-", "_");



            bundle.putString("dayName" + i , dayNameFormatted);
            bundle.putString("icon" + i , dayNameFormatted);
            i++;
        }



        return bundle;
    }

    private void populateWeatherData() {
        Bundle args = getArguments();

        for(int count = 0; count<7; count++) {

            String iView = "week_"+(count+1);
            int resName = getResources().getIdentifier(iView, "id", getActivity().getPackageName());
            View view1 = view.findViewById(resName);
            TextView day = view1.findViewById(R.id.w_day);
            WeatherIconView icon = view1.findViewById(R.id.w_icon);


            String temp_data = args.getString("dayName"+count);
            String iconName = args.getString("icon"+count);
            //int iconRes = getResources().getIdentifier(iconName, "drawable", getActivity().getPackageName());
            String weatherIconResource = getActivity().getString(getStringIdentifier(getActivity(), iconName));

            icon.setIconResource(weatherIconResource);
            day.setText(temp_data);
        }

    }
}
