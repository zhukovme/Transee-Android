package in.transee.transee.ui.activity;

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
import java.util.HashMap;
import java.util.List;

import in.transee.transee.R;
import in.transee.transee.api.Repository;
import in.transee.transee.data.city.City;
import in.transee.transee.data.transport.TransportItem;
import in.transee.transee.data.transport.Transports;
import in.transee.transee.data.transportListItem.TransportChooserObservable;
import in.transee.transee.data.transportListItem.TransportListItem;
import in.transee.transee.ui.adapter.RvPagerAdapter;
import in.transee.transee.ui.adapter.TransportChooserRvAdapter;
import rx.Observable;

/**
 * @author Michael Zhukov
 */
public class TransportChooserActivity extends AppCompatActivity {

    public static final String SELECTED_TRANSPORT_EXTRA = "selected_transport_extra";

    private ViewPager viewPager;
    private PagerSlidingTabStrip tabs;
    private ProgressBar progressBar;

    private TransportChooserObservable transportChooserObservable;

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
        FloatingActionButton fabLocateTransport = (FloatingActionButton)
                findViewById(R.id.fab_locate_transport);
        fabLocateTransport.hide();

        transportChooserObservable = new TransportChooserObservable(fabLocateTransport);

        startLoadingTransports();
    }

    public void onFabLocateTransportClick(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        HashMap selectedItems = (HashMap) transportChooserObservable.getSelectedItems();
        intent.putExtra(SELECTED_TRANSPORT_EXTRA, selectedItems);
        setResult(RESULT_OK, intent);
        onBackPressed();
    }

    private void startLoadingTransports() {
        List<RecyclerView> recyclerViews = new ArrayList<>();
        Repository.getInstance().getTransports(currentCity.getId())
                .doOnNext(transportsList -> recyclerViews.addAll(setupRecyclerViews(transportsList)))
                .flatMap(Observable::from)
                .map(transports -> transports.getName(this))
                .toList()
                .subscribe(
                        transportNames -> setupTabs(recyclerViews, transportNames),
                        throwable -> {
                            onError();
                            throwable.printStackTrace();
                        },
                        this::hideProgressBar);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    private void onError() {
        Snackbar
                .make(progressBar, getString(R.string.error_msg),
                        Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.action_retry, v -> {
                    startLoadingTransports();
                })
                .show();
    }

    private void setupTabs(List<RecyclerView> pages, List<String> tabTitles) {
        viewPager.setAdapter(new RvPagerAdapter(pages, tabTitles));
        tabs.setViewPager(viewPager);
    }

    private List<RecyclerView> setupRecyclerViews(List<Transports> transportsList) {
        List<RecyclerView> rvList = new ArrayList<>();
        for (Transports type : transportsList) {
            RecyclerView rv = new RecyclerView(this);
            rv.setAdapter(new TransportChooserRvAdapter(
                    setupTransportList(type),
                    transportChooserObservable));
            rv.setLayoutManager(new LinearLayoutManager(this));
            rv.setTag(type.getType());
            rvList.add(rv);
        }
        return rvList;
    }

    private List<TransportListItem> setupTransportList(Transports transports) {
        List<TransportListItem> transportList = new ArrayList<>();
        for (TransportItem item : transports.getItems()) {
            TransportListItem transportListItem = new TransportListItem();
            transportListItem.setType(transports.getType());
            transportListItem.setName(item.getName());
            transportListItem.setId(item.getId());
            transportList.add(transportListItem);
        }
        return transportList;
    }
}
