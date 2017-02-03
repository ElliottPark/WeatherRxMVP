package park.weatherapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by epark599 on 1/8/17.
 */

public class Forecast {

    @SerializedName("txt_forecast")
    private TxtForecast txtForecast;
    @SerializedName("simpleforecast")
    private SimpleForecast simpleForecast;

    public Forecast() {
    }

    public TxtForecast getTxtForecast() {
        return txtForecast;
    }

    public SimpleForecast getSimpleForecast() {
        return simpleForecast;
    }

    public Map<Integer, TxtForecast.ForecastDay> getTxtForcastDayMap() {
        if (this.txtForecast == null) {
            return null;
        }
        return this.txtForecast.getForecastMap();
    }

    public Map<Integer, SimpleForecast.ForecastDay> getSimpleForcastDayMap() {
        if (this.simpleForecast == null) {
            return null;
        }
        return this.simpleForecast.getForecastMap();
    }

}
