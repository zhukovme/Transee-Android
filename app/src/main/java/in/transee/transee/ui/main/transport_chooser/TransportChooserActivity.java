package in.transee.transee.ui.main.transport_chooser;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import in.transee.transee.R;
import in.transee.transee.TranseeApplication;
import in.transee.transee.data.city.City;
import in.transee.transee.data.transport.TransportItem;
import in.transee.transee.data.transport.Transports;
import in.transee.transee.data.transport_list_item.TransportChooserObservable;
import in.transee.transee.data.transport_list_item.TransportListItem;
import in.transee.transee.injection.component.BaseActivityComponent;
import in.transee.transee.ui.base.BaseActivity;
import in.transee.transee.ui.main.map.MapActivity;

/**
 * @author Michael Zhukov
 */
public class TransportChooserActivity extends BaseActivity implements TransportChooserMvpView {

    public static final String CURRENT_CITY_EXTRA = "current_city_extra";

    @Inject TransportChooserPresenter presenter;

    private ViewPager viewPager;
    private TabLayout tlTransportChooser;
    private ProgressBar progressBar;

    private TransportChooserObservable transportChooserObservable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((TransportChooserActivityComponent) getComponent()).inject(this);
        setContentView(R.layout.activity_transport_chooser);
        initToolbar();

        City currentCity = (City) getIntent().getSerializableExtra(CURRENT_CITY_EXTRA);

        setToolbarTitle(currentCity.getName(this));

        viewPager = (ViewPager) findViewById(R.id.pager);
        tlTransportChooser = (TabLayout) findViewById(R.id.tl_transport_chooser);
        progressBar = (ProgressBar) findViewById(R.id.pb_transport_chooser);
        FloatingActionButton fabLocateTransport = (FloatingActionButton)
                findViewById(R.id.fab_locate_transport);
        fabLocateTransport.hide();
        fabLocateTransport.setOnClickListener(this::onFabLocateTransportClick);

        transportChooserObservable = new TransportChooserObservable(fabLocateTransport);

        presenter.attachView(this);
        presenter.setCurrentCity(currentCity.getId());
        presenter.loadTransports();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public BaseActivityComponent createComponent() {
        return DaggerTransportChooserActivityComponent.builder()
                .applicationComponent(TranseeApplication.get(this).getComponent())
                .build();
    }

    @Override
    public void showTransports(List<Transports> transportsList) {
        List<RecyclerView> recyclerViews = new ArrayList<>(setupRecyclerViews(transportsList));
        List<String> transportNames = new ArrayList<>();
        for (Transports transports : transportsList) {
            transportNames.add(transports.getName(this));
        }
        setupTabs(recyclerViews, transportNames);
    }

    @Override
    public void showTransportsEmpty() {
        // TODO: 28.06.2016 Make empty page
    }

    @Override
    public void showError(@StringRes int message) {
        Snackbar.make(toolbar, message,Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.action_retry, v -> presenter.loadTransports())
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

    private void setupTabs(List<RecyclerView> pages, List<String> tabTitles) {
        viewPager.setAdapter(new RvPagerAdapter(pages, tabTitles));
        tlTransportChooser.setupWithViewPager(viewPager);
    }

    private List<RecyclerView> setupRecyclerViews(List<Transports> transportsList) {
        List<RecyclerView> rvList = new ArrayList<>();
        for (Transports type : transportsList) {
            RecyclerView rv = new RecyclerView(this);
            rv.setAdapter(new TransportChooserRvAdapter(this, setupTransportList(type),
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

    private void onFabLocateTransportClick(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        HashMap selectedItems = (HashMap) transportChooserObservable.getSelectedItems();
        intent.putExtra(MapActivity.SELECTED_TRANSPORT_EXTRA, selectedItems);
        setResult(RESULT_OK, intent);
        onBackPressed();
    }
}
