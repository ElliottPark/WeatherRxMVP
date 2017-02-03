package park.weatherapp.view;

import rx.Observable;

/**
 * Created by epark599 on 1/8/17.
 */

public interface IForecastView {
    Observable<String> close();
}
