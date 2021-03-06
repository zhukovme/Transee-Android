package in.transee.transee.ui.main.map;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import in.transee.transee.R;
import in.transee.transee.TranseeApplication;
import in.transee.transee.data.city.City;
import in.transee.transee.data.favorite.Favorite;
import in.transee.transee.data.position.Positions;
import in.transee.transee.data.route.Routes;
import in.transee.transee.data.transport.info.TransportInfo;
import in.transee.transee.injection.component.BaseActivityComponent;
import in.transee.transee.injection.module.ActivityModule;
import in.transee.transee.ui.base.BaseActivity;
import in.transee.transee.ui.main.city_chooser.CityChooserActivity;
import in.transee.transee.ui.main.settings.SettingsActivity;
import in.transee.transee.ui.main.transport_chooser.TransportChooserActivity;
import in.transee.transee.util.PermissionUtil;

/**
 * @author Michael Zhukov
 */
public class MapActivity extends BaseActivity implements OnMapReadyCallback, MapMvpView {

    public static final String CURRENT_CITY_EXTRA = "current_city_extra";
    public static final String SELECTED_TRANSPORT_EXTRA = "selected_transport_extra";
    public static final int TRANSPORT_CHOOSER_REQUEST = 1;

    private static final int REQUEST_LOCATION = 0;
    private static final String[] PERMISSIONS_LOCATION = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    @Inject MapPresenter presenter;
    @Inject GoogleMapPresenter googleMapPresenter;
    @Inject TransportInfoRvAdapter transportInfoAdapter;
    private FavoritesRvAdapter favoritesAdapter;

    private DrawerLayout drawer;
    private FloatingActionButton fabChooseTransport;
    private BottomSheetBehavior bsBehavior;
    private TextView tvTransportName;
    private TextView tvTransportGosId;
    private ProgressBar pbTransportInfo;
    private ImageView ivFavorite;

    private City currentCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MapActivityComponent) getComponent()).inject(this);
        setContentView(R.layout.activity_map);
        initToolbar();

        currentCity = (City) getIntent().getSerializableExtra(CURRENT_CITY_EXTRA);

        setToolbarTitle(currentCity.getName(this));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        drawer.setDrawerShadow(R.drawable.drawer_dropshadow, GravityCompat.START);
        toggle.syncState();

        tvTransportName = (TextView) findViewById(R.id.tv_transport_name);
        tvTransportGosId = (TextView) findViewById(R.id.tv_transport_gos_id);
        pbTransportInfo = (ProgressBar) findViewById(R.id.pb_transport_info);
        RecyclerView rvTransportInfo = (RecyclerView) findViewById(R.id.rv_transport_info);
        rvTransportInfo.setLayoutManager(new LinearLayoutManager(this));
        rvTransportInfo.setAdapter(transportInfoAdapter);

        ivFavorite = (ImageView) findViewById(R.id.iv_favorite);

        RecyclerView rvFavorites = (RecyclerView) findViewById(R.id.rv_favorites);
        rvFavorites.setLayoutManager(new LinearLayoutManager(this));
        favoritesAdapter = new FavoritesRvAdapter(this, presenter);
        rvFavorites.setAdapter(favoritesAdapter);

        fabChooseTransport = (FloatingActionButton) findViewById(R.id.fab_choose_transport);
        fabChooseTransport.setOnClickListener(v -> {
            Intent intent = new Intent(this, TransportChooserActivity.class);
            intent.putExtra(TransportChooserActivity.CURRENT_CITY_EXTRA, currentCity);
            startActivityForResult(intent, MapActivity.TRANSPORT_CHOOSER_REQUEST);
        });

        View bsView = findViewById(R.id.bs_transport_info);
        bsBehavior = BottomSheetBehavior.from(bsView);
        setupBottomSheet();
        bsView.setOnClickListener(v -> bottomSheetOnClick());

        googleMapPresenter.attachView(this);
        presenter.attachView(this);
        presenter.setCurrentCity(currentCity.getId());
    }

    @Override
    protected void onResume() {
        super.onResume();
        favoritesAdapter.clear();
        presenter.loadFavorites();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (googleMapPresenter != null) {
            googleMapPresenter.clear();
        }
        presenter.detachView();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (bsBehavior.getState() != BottomSheetBehavior.STATE_HIDDEN) {
            hideBottomSheet();
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TRANSPORT_CHOOSER_REQUEST && resultCode == RESULT_OK) {
            Map selectedTransport = (HashMap) data.getSerializableExtra(SELECTED_TRANSPORT_EXTRA);
            presenter.setTransportIds(selectedTransport);
            presenter.loadTransport();
        }
    }

    @SuppressWarnings("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMapPresenter.setGoogleMap(googleMap);
        googleMapPresenter.moveCamera(currentCity.getCoordinates(), 12);
        if (PermissionUtil.checkLocationPermissions(this)) {
            requestLocationPermissions();
        } else {
            googleMap.setMyLocationEnabled(true);
        }
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMapPresenter.setInfoWindowAdapter();
        googleMap.setOnMarkerClickListener(marker -> {
            marker.showInfoWindow();
            showBottomSheetShort();

            String[] snippet = marker.getSnippet().split("/");
            String type = snippet[0];
            String gosId = snippet[1];
            String itemId = snippet[2];

            tvTransportName.setText(marker.getTitle());
            tvTransportGosId.setText(gosId);
            setupFavoritesButton(marker.getTitle());

            presenter.setType(type);
            presenter.setGosId(gosId);
            presenter.setItemId(itemId);

            presenter.loadTransportInfo();
            return true;
        });
        googleMap.setOnMapClickListener(latLng -> hideBottomSheet());
    }

    private void setupFavoritesButton(String transportName) {
        ivFavorite.setImageResource(presenter.isFavorite(transportName) ?
                R.drawable.ic_star_accent_48 : R.drawable.ic_star_outline_accent_48);

        ivFavorite.setOnClickListener(v -> {
            if (presenter.isFavorite(transportName)) {
                ivFavorite.setImageResource(R.drawable.ic_star_outline_accent_48);
                presenter.deleteFromFavorite(transportName);
            } else {
                ivFavorite.setImageResource(R.drawable.ic_star_accent_48);
                presenter.addToFavorite(transportName);
            }
            favoritesAdapter.clear();
            presenter.loadFavorites();
        });
    }

    @Override
    public BaseActivityComponent createComponent() {
        return DaggerMapActivityComponent.builder()
                .applicationComponent(TranseeApplication.get(this).getComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    @Override
    public void showTransportRoutes(List<Routes> routesList) {
        googleMapPresenter.drawRoutes(routesList);
    }

    @Override
    public void showTransportPositions(List<Positions> positionsList) {
        googleMapPresenter.drawPositions(positionsList);
    }

    @Override
    public void clearMap() {
        hideBottomSheet();
        googleMapPresenter.clear();
        googleMapPresenter.moveCamera(currentCity.getCoordinates(), 12);
    }

    @Override
    public void showErrorTransportPositions(@StringRes int message) {
        Snackbar
                .make(fabChooseTransport, message, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.action_retry, v -> presenter.loadTransport())
                .show();
    }

    public void setupBottomSheet() {
        bsBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bsBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                    case BottomSheetBehavior.STATE_EXPANDED:
                        fabChooseTransport.hide();
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        fabChooseTransport.show();
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
    }

    public void showBottomSheetShort() {
        int headerHeight = getResources().getInteger(R.integer.bottom_sheet_header_height);
        if (bsBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
            bsBehavior.setPeekHeight(headerHeight);
            bsBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    public void hideBottomSheet() {
        bsBehavior.setPeekHeight(0);
        bsBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    public void bottomSheetOnClick() {

        switch (bsBehavior.getState()) {
            case BottomSheetBehavior.STATE_COLLAPSED:
                bsBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case BottomSheetBehavior.STATE_EXPANDED:
                int headerHeight = getResources().getInteger(R.integer.bottom_sheet_header_height);
                bsBehavior.setPeekHeight(headerHeight);
                bsBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                break;
        }
    }

    @Override
    public void showTransportInfo(List<TransportInfo> transportInfoList) {
        transportInfoAdapter.setData(transportInfoList);
    }

    @Override
    public void showTransportInfoEmpty() {
        transportInfoAdapter.clear();
    }

    @Override
    public void showTransportInfoProgressBar() {
        pbTransportInfo.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideTransportInfoProgressBar() {
        pbTransportInfo.setVisibility(View.GONE);
    }

    @Override
    public void showErrorTransportInfo(@StringRes int message) {
        Snackbar
                .make(fabChooseTransport, message, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.action_retry, v -> presenter.loadTransportInfo())
                .show();
    }

    @Override
    public void showFavorites(List<Favorite> favorites) {
        favoritesAdapter.setData(favorites);
    }

    public void onChangeCityClick(View view) {
        startActivity(new Intent(this, CityChooserActivity.class));
        onBackPressed();
        finish();
    }

    public void onSettingsClick(View view) {
        SettingsActivity.start(this);
        onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (PermissionUtil.verifyPermissions(grantResults)) {
                Snackbar.make(fabChooseTransport, R.string.location_permissions_granted,
                        Snackbar.LENGTH_SHORT)
                        .show();
            } else {
                Snackbar.make(fabChooseTransport, R.string.permissions_not_granted,
                        Snackbar.LENGTH_SHORT)
                        .show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void requestLocationPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                || ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)) {

            Snackbar.make(fabChooseTransport, R.string.request_location_permissions,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, view -> ActivityCompat.requestPermissions(this,
                            PERMISSIONS_LOCATION, REQUEST_LOCATION))
                    .show();
        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS_LOCATION, REQUEST_LOCATION);
        }
    }
}
