package in.transee.transee.ui.main.city_chooser;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import javax.inject.Inject;

import in.transee.transee.R;
import in.transee.transee.TranseeApplication;
import in.transee.transee.data.city.City;
import in.transee.transee.injection.component.BaseActivityComponent;
import in.transee.transee.injection.module.ActivityModule;
import in.transee.transee.ui.base.BaseActivity;

/**
 * @author Michael Zhukov
 */
public class CityChooserActivity extends BaseActivity implements CityChooserMvpView {

    @Inject CityChooserPresenter presenter;
    @Inject CityChooserRvAdapter adapter;

    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((CityChooserActivityComponent) getComponent()).inject(this);
        setContentView(R.layout.activity_city_chooser);
        initToolbar();
        setToolbarTitle(R.string.choose_city_title);

        progressBar = (ProgressBar) findViewById(R.id.pb_city_chooser);

        RecyclerView rvCities = (RecyclerView) findViewById(R.id.rv_cities);
        rvCities.setLayoutManager(new LinearLayoutManager(this));
        rvCities.setAdapter(adapter);

        presenter.attachView(this);
        presenter.loadCities();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public BaseActivityComponent createComponent() {
        return DaggerCityChooserActivityComponent.builder()
                .applicationComponent(TranseeApplication.get(this).getComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    @Override
    public void showCities(List<City> cities) {
        adapter.setData(cities);
    }

    @Override
    public void showCitiesEmpty() {
        adapter.clear();
    }

    @Override
    public void showError(@StringRes int message) {
        Snackbar
                .make(toolbar, message, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.action_retry, v -> presenter.loadCities())
                .show();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}
