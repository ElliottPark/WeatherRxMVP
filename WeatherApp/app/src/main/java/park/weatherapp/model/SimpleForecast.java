package park.weatherapp.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by epark599 on 1/8/17.
 */

public class SimpleForecast {

    public static class ForecastDay {

        private ForecastDate date;
        private int period;
        private Temp high;
        private Temp low;
        private String conditions;
        private String icon_url;
        private int avehumidity;
        private int maxhumidity;
        private int minhumidity;

        public ForecastDate getDate() {
            return date;
        }

        public int getPeriod() {
            return period;
        }

        public Temp getHigh() {
            return high;
        }

        public Temp getLow() {
            return low;
        }

        public String getConditions() {
            return conditions;
        }

        public String getIcon_url() {
            return icon_url;
        }

        public int getAvehumidity() {
            return avehumidity;
        }

        public int getMaxhumidity() {
            return maxhumidity;
        }

        public int getMinhumidity() {
            return minhumidity;
        }
    }

    private List<ForecastDay> forecastday;
    private Map<Integer, ForecastDay> forecastDayMap;

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
