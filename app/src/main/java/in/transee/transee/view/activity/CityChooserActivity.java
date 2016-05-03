package in.transee.transee.view.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import in.transee.transee.R;
import in.transee.transee.api.Repository;
import in.transee.transee.view.adapter.CityChooserRvAdapter;

/**
 * @author Michael Zhukov
 */
public class CityChooserActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private RecyclerView rvCities;

    private CityChooserRvAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_chooser);

        progressBar = (ProgressBar) findViewById(R.id.pb_city_chooser);

        rvCities = (RecyclerView) findViewById(R.id.rv_cities);
        rvCities.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CityChooserRvAdapter(this);
        rvCities.setAdapter(adapter);

        startLoadingCities();
    }

    private void startLoadingCities() {
        Repository.getInstance().getCities()
                .subscribe(
                        cities -> adapter.setData(cities),
                        throwable -> {
                            onError();
                            throwable.printStackTrace();
                        },
                        this::hideProgressBar);
    }

    private void onError() {
        Snackbar
                .make(rvCities, getString(R.string.error_msg_snackbar), Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry_snack_action, v -> {
                    startLoadingCities();
                })
                .show();
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}
