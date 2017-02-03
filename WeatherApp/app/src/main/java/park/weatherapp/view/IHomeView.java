package park.weatherapp.view;

import rx.Observable;

/**
 * Created by epark599 on 1/8/17.
 */

public interface IHomeView {
    Observable<String> location();
    Observable<String> numDays();
    Observable<Void> fetch();
}
