package park.weatherapp.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by epark599 on 1/8/17.
 */

public class TxtForecast {

    public static class ForecastDay {

        private int period;
        private String icon;
        private String icon_url;
        private String title;
        private String fcttext;
        private String fcttext_metric;

        public int getPeriod() {
            return period;
        }

        public String getIcon() {
            return icon;
        }

        public String getIcon_url() {
            return icon_url;
        }

        public String getTitle() {
            return title;
        }

        public String getFcttext() {
            return fcttext;
        }

        public String getFcttext_metric() {
            return fcttext_metric;
        }
    }

    private String date;
    private List<TxtForecast.ForecastDay> forecastday;
    Map<Integer, ForecastDay> forecastDayMap;

    public String getDate() {
        return date;
    }

    public List<ForecastDay> getForecastday() {
        return forecastday;
    }


    public Map<Integer, ForecastDay> getForecastMap() {
        if (this.forecastday == null) {
            return null;
        }
        if (forecastDayMap == null) {
            forecastDayMap= new HashMap<>();
            for (ForecastDay forecastDay : forecastday) {
                forecastDayMap.put(forecastDay.getPeriod(), forecastDay);
            }
        }
        return forecastDayMap;
    }
}
