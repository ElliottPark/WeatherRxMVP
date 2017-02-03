package park.weatherapp.view;

import android.app.ProgressDialog;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Map;

import park.weatherapp.R;
import park.weatherapp.model.Forecast;
import park.weatherapp.model.FetchData;
import park.weatherapp.model.SimpleForecast;
import park.weatherapp.model.TxtForecast;
import park.weatherapp.presenter.ForecastPresenter;
import park.weatherapp.presenter.IForecastPresenter;
import rx.Observable;
import rx.Observer;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by epark599 on 1/8/17.
 */

public class ForecastTabbedActivity extends AppCompatActivity implements IForecastView {

    private static final String TAG = ForecastTabbedActivity.class.getSimpleName();

    private SectionsPagerAdapter sectionsPagerAdapter;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ProgressDialog progressDialog;
    private String location;
    private int numDays;

    private final CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_tabbed);

        this.location = getIntent().getStringExtra(FetchData.LOCATION_KEY);
        if (location == null) {
            Log.e(TAG, "Error: onCreate(): Location is null");
            finish();
            return;
        }
        this.numDays = getIntent().getIntExtra(FetchData.NUM_DAYS_KEY, FetchData.DEFAULT_NUM_DAYS);

        initView();

        showProgressDialog();
        IForecastPresenter presenter = new ForecastPresenter();
        this.compositeSubscription
                .add(presenter
                        .forecast(location)
                        .subscribe(displayForecastData()));
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.progressDialog = new ProgressDialog(this);

        this.tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        this.viewPager = (ViewPager) findViewById(R.id.container);

        this.tabLayout.setupWithViewPager(this.viewPager);

        getSupportActionBar().setTitle(this.location);
        for (int i = 0; i < this.numDays; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.day_format, i+1)));
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout.setEnabled(false);

    }

    @Override
    protected void onDestroy() {
        this.compositeSubscription.unsubscribe();
        super.onDestroy();
    }

    private Observer<Forecast> displayForecastData() {
        return new Observer<Forecast>() {

            @Override
            public void onCompleted() {
                Log.e(TAG, "ForecastTabbedActivity.displayForecastData.onError()");

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "ForecastTabbedActivity.displayForecastData.onError()");
                finish();
            }

            @Override
            public void onNext(Forecast forecast) {
                dismissProgressDialog();
                if (forecast != null) {
                    setForecast(forecast);
                } else {
                    Log.e(TAG, "onNext(): forecast is null");
                    finish();
                }
            }
        };
    }

    private void showProgressDialog() {
        this.progressDialog.setMessage(getString(R.string.progress_message));
        this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        this.progressDialog.setIndeterminate(true);
        this.progressDialog.show();
    }

    private void dismissProgressDialog() {
        this.progressDialog.dismiss();
    }

    private void setForecast(Forecast forecast) {
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this.numDays, forecast);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabLayout.setEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_forecast_tabbed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Observable<String> close() {
        return null;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private int numDays;
        private Map<Integer, TxtForecast.ForecastDay> txtForecastDayMap;
        private Map<Integer, SimpleForecast.ForecastDay> simpleForecastDayMap;

        public SectionsPagerAdapter(FragmentManager fm, int numDays, Forecast forecast) {
            super(fm);
            this.numDays = numDays;
            this.txtForecastDayMap = forecast.getTxtForcastDayMap();
            this.simpleForecastDayMap = forecast.getSimpleForcastDayMap();
        }

        @Override
        public Fragment getItem(int position) {
            // TODO: This should be more in line with the RxMVP architecture
            TxtForecast.ForecastDay txtForecastDay = null;
            TxtForecast.ForecastDay txtForecastNight = null;
            if (this.txtForecastDayMap != null) {
                txtForecastDay = this.txtForecastDayMap.get(position * 2);
                txtForecastNight = this.txtForecastDayMap.get(position * 2 + 1);
            }
            SimpleForecast.ForecastDay simpleForecastDay = null;
            if (this.simpleForecastDayMap != null) {
                simpleForecastDay = this.simpleForecastDayMap.get(position+1);
            }
            return ForecastFragment.newInstance(position + 1, txtForecastDay, txtForecastNight, simpleForecastDay);
        }

        @Override
        public int getCount() {
            return numDays;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            if (0 <= position && position < numDays) {
                return getString(R.string.day_format, position+1);
            }
            return null;
        }
    }
}
