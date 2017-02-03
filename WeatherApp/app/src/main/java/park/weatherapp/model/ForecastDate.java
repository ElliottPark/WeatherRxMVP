package park.weatherapp.model;

/**
 * Created by epark599 on 1/8/17.
 */

public class ForecastDate {

    private String pretty;
    private int day;
    private int month;
    private int year;
    private String monthname;
    private String weekday;

    public String getPretty() {
        return pretty;
    }

    public String getWeekday() {
        return weekday;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public String getMonthname() {
        return monthname;
    }
}
