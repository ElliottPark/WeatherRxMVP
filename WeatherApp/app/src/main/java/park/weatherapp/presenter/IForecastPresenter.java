package park.weatherapp.presenter;

import park.weatherapp.model.Forecast;
import rx.Observable;

/**
 * Created by epark599 on 1/8/17.
 */

public interface IForecastPresenter {
    Observable<Forecast> forecast(String location);
}
