package park.weatherapp.services;

import park.weatherapp.model.ForecastResponse;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by epark599 on 1/8/17.
 */

public interface WeatherUndergroundAPIService {

    // TODO: Obfuscate the API key
    @GET("api/<API KEY GOES HERE>/forecast10day/q/{location}.json")
    Observable<ForecastResponse> getResults(@Path("location") String location);

    @GET("api/<API KEY GOES HERE>/forecast10day/q/{state}/{city}.json")
    Observable<ForecastResponse> getForecast(@Path("state") String state, @Path("city") String city);
}
