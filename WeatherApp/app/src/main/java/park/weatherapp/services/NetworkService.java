package park.weatherapp.services;

import com.google.gson.Gson;

import park.weatherapp.model.Forecast;
import park.weatherapp.model.Result;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.AsyncSubject;

/**
 * Created by epark599 on 1/8/17.
 */

/*
 * This implements an interface so it will be easy to stub it out when unit testing components
 * that rely on it, e.g. the ForecastPresenter
 */
public class NetworkService implements INetworkService {

    private static INetworkService instance;
    private static final String BASE_URL = "http://api.wunderground.com/";
    // Weather forecast information doesn't update that often.
    private static final long FIVE_MINUTES = 1000 * 60 * 5;

    private WeatherUndergroundAPIService weatherUndergroundAPIService;
    private AsyncSubject<Forecast> forecast;

    private String lastLocation;
    private long lastApiRequestTime = 0l;

    private NetworkService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        weatherUndergroundAPIService = retrofit.create(WeatherUndergroundAPIService.class);
    }

    public static INetworkService getInstance() {
        if (instance == null) {
            instance = new NetworkService();
        }
        return instance;
    }

    @Override
    public Observable<Forecast> fetchData(String location) {

        if (makeNewAPICall(location)) {

            lastLocation = location;
            lastApiRequestTime = System.currentTimeMillis();
            forecast = AsyncSubject.create();
            weatherUndergroundAPIService.getResults(location)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(forecastResponse -> forecastResponse.getResponse().getResults().get(0))
                    .flatMap(new Func1<Result, Observable<Forecast>>() {
                        @Override
                        public Observable<Forecast> call(Result result) {
                            return weatherUndergroundAPIService.getForecast(result.getState(), result.getCity())
                                    .subscribeOn(Schedulers.newThread())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .map(forecastResponse -> forecastResponse.getForecast());
                        }
                    })
                    .subscribe(forecast);
        }
        return forecast;
    }

    private boolean makeNewAPICall(String location) {
        return (lastLocation == null || !location.equals(lastLocation) ||
                forecast == null || forecast.hasCompleted() ||
                (System.currentTimeMillis() - lastApiRequestTime) > FIVE_MINUTES);
    }

}
