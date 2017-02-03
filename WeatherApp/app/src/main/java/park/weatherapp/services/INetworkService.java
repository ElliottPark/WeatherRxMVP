package park.weatherapp.services;

import park.weatherapp.model.Forecast;
import rx.Observable;

/**
 * Created by epark599 on 1/9/17.
 */

/*
 * Making this an interface makes it easier to stub, so components that rely on it,
 * like a presenter, are easier to unit test
 */
public interface INetworkService {
    Observable<Forecast> fetchData(String location);
}
