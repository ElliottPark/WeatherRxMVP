package park.weatherapp.model;

/**
 * Created by epark599 on 1/8/17.
 */

public class FetchData {
    public static final String LOCATION_KEY = "LOCATION_KEY";
    public static final String NUM_DAYS_KEY = "NUM_DAYS_KEY";
    public static final int DEFAULT_NUM_DAYS = 10;

    private String location;
    private int numDays;

    public FetchData(String location, int numDays) {
        this.location = location;
        this.numDays = numDays;
    }

    public String getLocation() {
        return location;
    }

    public int getNumDays() {
        return numDays;
    }
}
