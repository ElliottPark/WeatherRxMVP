package park.weatherapp.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import park.weatherapp.R;
import park.weatherapp.model.ForecastDate;
import park.weatherapp.model.SimpleForecast;
import park.weatherapp.model.Temp;
import park.weatherapp.model.TxtForecast;

/**
 * Created by epark599 on 1/9/17.
 */

public class ForecastFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private String iconURL;
    private String high;
    private String low;
    private String conditions;
    private String day;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ForecastFragment newInstance(int sectionNumber, TxtForecast.ForecastDay txtForecastDay,
                                               TxtForecast.ForecastDay txtForecastNight,
                                               SimpleForecast.ForecastDay simpleForecastDay) {
        ForecastFragment fragment = new ForecastFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        fragment.setForecastDays(txtForecastDay, txtForecastNight, simpleForecastDay);
        return fragment;
    }

    private void setForecastDays(TxtForecast.ForecastDay txtForecastDay,
                                 TxtForecast.ForecastDay txtForecastNight,
                                 SimpleForecast.ForecastDay simpleForecastDay) {
        // TODO: Replace all of these null checks with validation logic in the presenter
        if (txtForecastDay != null) {
            // TODO: Make use of this in the future
        }
        if (txtForecastNight != null) {
            // TODO: Make use of this in the future
        }
        if (simpleForecastDay != null) {
            this.conditions = simpleForecastDay.getConditions();
            this.iconURL = simpleForecastDay.getIcon_url();
            Temp temp = simpleForecastDay.getHigh();
            if (temp != null) {
                this.high = temp.getFahrenheit();
            }
            temp = simpleForecastDay.getLow();
            if (temp != null) {
                this.low = temp.getFahrenheit();
            }
            ForecastDate date = simpleForecastDay.getDate();
            if (date != null) {
                this.day = date.getWeekday();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_forecast_tabbed, container, false);

        if (savedInstanceState != null) {
            restore(savedInstanceState);
        }

        // TODO: Add null checks or validation logic in the presenter

        TextView dayTextView = (TextView) rootView.findViewById(R.id.day_text_view);
        dayTextView.setText(this.day);

        ImageView iconImageView = (ImageView) rootView.findViewById(R.id.icon_image_view);
        Picasso.with(getContext()).load(iconURL).into(iconImageView);

        TextView conditionsTextView = (TextView) rootView.findViewById(R.id.conditions_text_view);
        conditionsTextView.setText(this.conditions);

        TextView highTextView = (TextView) rootView.findViewById(R.id.high_text_view);
        highTextView.setText(this.high+"f");

        TextView lowTextView = (TextView) rootView.findViewById(R.id.low_text_view);
        lowTextView.setText(this.low+"f");

        return rootView;
    }

    private void restore(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey("day")) {
            this.day = savedInstanceState.getString("day");
        }
        if (savedInstanceState.containsKey("conditions")) {
            this.conditions = savedInstanceState.getString("conditions");
        }
        if (savedInstanceState.containsKey("high")) {
            this.high = savedInstanceState.getString("high");
        }
        if (savedInstanceState.containsKey("low")) {
            this.low = savedInstanceState.getString("low");
        }
        if (savedInstanceState.containsKey("iconUrl")) {
            this.iconURL = savedInstanceState.getString("iconUrl");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("day", this.day);
        outState.putString("conditions", this.conditions);
        outState.putString("high", this.high);
        outState.putString("low", this.low);
        outState.putString("iconUrl", this.iconURL);
    }
}