package park.weatherapp.presenter;

import android.util.Log;
import park.weatherapp.model.FetchData;
import park.weatherapp.view.IHomeView;
import rx.Observable;
import rx.Observer;
import rx.subjects.PublishSubject;

/**
 * Created by epark599 on 1/8/17.
 */

public class HomePresenter implements IHomePresenter {

    public static final String TAG = HomePresenter.class.getSimpleName();
    private final PublishSubject<FetchData> searchDataSubject = PublishSubject.create();
    private final PublishSubject<Void> numDaysInvalidSubject = PublishSubject.create();
    private String location;
    private int numDays;

    @Override
    public void registerView(IHomeView searchView) {
        searchView.location().subscribe(s -> {
            location = s;
        });
        searchView.numDays().subscribe(s -> {
            try {
                numDays = Integer.parseInt(s);
                if (numDays < 1 || 10 < numDays) {
                    numDaysInvalidSubject.onNext(null);
                    numDays = 0;
                }
            } catch (NumberFormatException e) {
                Log.e(TAG, e.getMessage());
                numDaysInvalidSubject.onNext(null);
            }
        });
        searchView.fetch().subscribe(fetchObserver());
    }

    private Observer<Void> fetchObserver() {
        return new Observer<Void>() {

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                // TODO: Return an error message through an error observable
                Log.e(TAG, "fetchObserver.onError");
            }

            @Override
            public void onNext(Void aVoid) {
                if (numDays > 0 && numDays < 11 && location != null) {
                    searchDataSubject.onNext(new FetchData(location, numDays));
                } else {
                    // TODO: Return an error message
                    Log.e(TAG, "fetchObserver.onNext null");
                }
            }
        };
    }

    @Override
    public Observable<Void> numDaysInvalid() {
        return this.numDaysInvalidSubject;
    }

    @Override
    public Observable<FetchData> fetch() {
        return this.searchDataSubject;
    }
}
