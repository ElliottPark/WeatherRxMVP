package park.weatherapp.presenter;

import park.weatherapp.model.FetchData;
import park.weatherapp.view.IHomeView;
import rx.Observable;

/**
 * Created by epark599 on 1/8/17.
 */

public interface IHomePresenter {

    void registerView(IHomeView searchView);
    Observable<Void> numDaysInvalid();
    Observable<FetchData> fetch();
}
