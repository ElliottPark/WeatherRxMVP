package park.weatherapp.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import park.weatherapp.R;
import park.weatherapp.model.FetchData;
import park.weatherapp.presenter.IHomePresenter;
import park.weatherapp.presenter.HomePresenter;
import rx.Observable;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by epark599 on 1/8/17.
 */

public class HomeActivity extends AppCompatActivity implements IHomeView {

    private final CompositeSubscription compositeSubscription = new CompositeSubscription();;
    private final PublishSubject<String> location = PublishSubject.create();
    private final PublishSubject<String> numDays = PublishSubject.create();
    private final PublishSubject<Void> fetch = PublishSubject.create();

    private boolean numDaysEventsEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);

        IHomePresenter presenter = new HomePresenter();
        presenter.registerView(this);


        EditText locationEditText = (EditText) findViewById(R.id.location_edit_text);
        locationEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                location.onNext(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        this.numDaysEventsEnabled = true;
        EditText numDaysEditText = (EditText) findViewById(R.id.num_days_edit_text);
        numDaysEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (numDaysEventsEnabled) {
                    numDays.onNext(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Button fetchButton = (Button) findViewById(R.id.fetch_button);
        fetchButton.setOnClickListener(v -> {
            fetch.onNext(null);
        });

        compositeSubscription.add(presenter.numDaysInvalid().subscribe(__ -> {
            numDaysEventsEnabled = false;
            numDaysEditText.setText("");
            numDaysEventsEnabled = true;
        }));
        compositeSubscription.add(presenter.fetch().subscribe(fetchForecast()));
    }

    @Override
    protected void onDestroy() {
        this.compositeSubscription.unsubscribe();
        super.onDestroy();
    }

    @Override
    public Observable<String> location() {
        return location;
    }

    @Override
    public Observable<String> numDays() {
        return numDays;
    }

    @Override
    public Observable<Void> fetch() {
        return fetch;
    }

    private Action1<FetchData> fetchForecast() {
        return searchData -> {
            Intent intent = new Intent(HomeActivity.this, ForecastTabbedActivity.class);
            intent.putExtra(FetchData.LOCATION_KEY, searchData.getLocation());
            intent.putExtra(FetchData.NUM_DAYS_KEY, searchData.getNumDays());
            startActivity(intent);
        };
    }
}
