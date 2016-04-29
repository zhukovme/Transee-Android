package in.transee.transee.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.transee.transee.R;
import in.transee.transee.api.Repository;
import in.transee.transee.data.transportListItem.TransportListItem;
import in.transee.transee.data.city.City;
import in.transee.transee.data.transport.TransportItem;
import in.transee.transee.data.transport.TransportType;
import in.transee.transee.data.transportListItem.TransportListItemsObservable;
import in.transee.transee.view.adapter.RvPagerAdapter;
import in.transee.transee.view.adapter.TransportChooserRvAdapter;
import rx.functions.Action1;

/**
 * @author Michael Zhukov
 */
public class TransportChooserActivity extends AppCompatActivity {

    public static final String SELECTED_TRANSPORT_EXTRA = "selected_transport_extra";

    private ViewPager viewPager;
    private PagerSlidingTabStrip tabs;
    private FloatingActionButton fabApplyTransport;
    private ProgressBar progressBar;

    private TransportListItemsObservable transportListItemsObservable;

    private City currentCity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_chooser);

        currentCity = (City) getIntent().getSerializableExtra(MapActivity.CURRENT_CITY_EXTRA);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(currentCity.getName(this));
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.pager);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        progressBar = (ProgressBar) findViewById(R.id.pb_transport_chooser);
        fabApplyTransport = (FloatingActionButton) findViewById(R.id.fab_apply_transport);
        fabApplyTransport.hide();

        transportListItemsObservable = new TransportListItemsObservable(fabApplyTransport);

        startLoadingTransports();
    }

    public void onFabApplyTransportClick(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        transportListItemsObservable.asMapObservable()
                .subscribe(map -> {
                    intent.putExtra(SELECTED_TRANSPORT_EXTRA, (HashMap) map);
                });
        setResult(RESULT_OK, intent);
        onBackPressed();
    }

    private void startLoadingTransports() {
        Repository.INSTANCE.getTransports(currentCity.getName(this))
                .subscribe(
                        transportTypes -> setupTabs(transportTypes),
                        throwable -> {
                            onError(getString(R.string.not_available_msg));
                            throwable.printStackTrace();
                        },
                        this::hideProgressBar);
    }

    private void onError(String errorMsg) {
        Snackbar
                .make(progressBar, errorMsg, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry_snack_action, v -> {
                    startLoadingTransports();
                })
                .show();
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    private void setupTabs(List<TransportType> transportList) {
        viewPager.setAdapter(new RvPagerAdapter(
                setupRecyclerViews(transportList),
                setupTabTitles(transportList)));
        tabs.setViewPager(viewPager);
    }

    private List<String> setupTabTitles(List<TransportType> transportList) {
        List<String> tabTitles = new ArrayList<>();
        for (TransportType type : transportList) {
            tabTitles.add(getString(
                    getResources().getIdentifier(type.getType(), "string", getPackageName())));
        }
        return tabTitles;
    }

    private List<RecyclerView> setupRecyclerViews(List<TransportType> transportList) {
        List<RecyclerView> rvList = new ArrayList<>();
        for (TransportType type : transportList) {
            RecyclerView rv = new RecyclerView(this);
            rv.setAdapter(new TransportChooserRvAdapter(
                    setupTransportList(type),
                    transportListItemsObservable));
            rv.setLayoutManager(new LinearLayoutManager(this));
            rv.setTag(type.getType());
            rvList.add(rv);
        }
        return rvList;
    }

    private List<TransportListItem> setupTransportList(TransportType type) {
        List<TransportListItem> transportList = new ArrayList<>();
        for (TransportItem item : type.getItems()) {
            TransportListItem transportListItem = new TransportListItem();
            transportListItem.setType(type.getType());
            transportListItem.setName(item.getName());
            transportListItem.setId(item.getId());
            transportList.add(transportListItem);
        }
        return transportList;
    }
}
