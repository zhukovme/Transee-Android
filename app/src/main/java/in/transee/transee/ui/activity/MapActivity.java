package in.transee.transee.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.HashMap;
import java.util.List;

import in.transee.transee.R;
import in.transee.transee.data.city.City;
import in.transee.transee.presenter.MapPresenter;

/**
 * @author Michael Zhukov
 */
public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final String CURRENT_CITY_EXTRA = "current_city_extra";
    public static final int TRANSPORT_CHOOSER_REQUEST = 1;

    private DrawerLayout drawer;
    private FloatingActionButton fabMapMain;
    private BottomSheetBehavior bsTransportInfoBehavior;

    private City currentCity;
    private MapPresenter mapPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        currentCity = (City) getIntent().getSerializableExtra(CURRENT_CITY_EXTRA);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(currentCity.getName(this));
        setSupportActionBar(toolbar);

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

        fabMapMain = (FloatingActionButton) findViewById(R.id.fab_map_main);

        View bsTransportInfo = findViewById(R.id.bs_transport_info);
        bsTransportInfoBehavior = BottomSheetBehavior.from(bsTransportInfo);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapPresenter.clearAndUnsubscribe();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TRANSPORT_CHOOSER_REQUEST:
                if (resultCode == RESULT_OK) {
                    HashMap selectedTransport = (HashMap<String, List<String>>)
                            data.getSerializableExtra(
                                    TransportChooserActivity.SELECTED_TRANSPORT_EXTRA);
                    mapPresenter.locateTransports(selectedTransport);
                    mapPresenter.setupCamera();
                }
        }
    }

    public void onFabChooseTransportClick(View view) {
        Intent intent = new Intent(this, TransportChooserActivity.class);
        intent.putExtra(MapActivity.CURRENT_CITY_EXTRA, currentCity);
        startActivityForResult(intent, MapActivity.TRANSPORT_CHOOSER_REQUEST);
    }

    public void onSettingsClick(View view) {
        Snackbar.make(fabMapMain, "Settings", Snackbar.LENGTH_LONG).show();
        onBackPressed();
    }

    public void onChangeCityClick(View view) {
        startActivity(new Intent(this, CityChooserActivity.class));
        onBackPressed();
        finish();
    }

    public View getView() {
        return fabMapMain;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapPresenter = new MapPresenter(this, googleMap, currentCity);
        mapPresenter.setupCamera();
        mapPresenter.setupButtons();
        mapPresenter.setupInfoWindowAdapter(bsTransportInfoBehavior, fabMapMain);
    }
}
