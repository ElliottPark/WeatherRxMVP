package park.weatherapp.presenter;

import park.weatherapp.model.Forecast;
import park.weatherapp.services.INetworkService;
import park.weatherapp.services.NetworkService;
import rx.Observable;

/**
 * Created by epark599 on 1/8/17.
 */

public class ForecastPresenter implements IForecastPresenter {

    private INetworkService networkService;

    public ForecastPresenter() {
        this.networkService = NetworkService.getInstance();
    }

    @Override
    public Observable<Forecast> forecast(String location) {
        return this.networkService.fetchData(location);
    }
}
