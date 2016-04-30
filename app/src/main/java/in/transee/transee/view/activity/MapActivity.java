package in.transee.transee.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
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
    private FloatingActionsMenu fam;

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
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        drawer.setDrawerShadow(R.drawable.drawer_dropshadow, GravityCompat.START);
        toggle.syncState();


        fam = (FloatingActionsMenu) findViewById(R.id.fam);
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
                }
        }
    }

    public void onFabScheduleClick(View view) {
        Snackbar.make(view, "Schedule", Snackbar.LENGTH_LONG).show();
        fam.collapse();
    }

    public void onFabNextToMeClick(View view) {
        Snackbar.make(view, "Next To Me", Snackbar.LENGTH_LONG).show();
        fam.collapse();
    }

    public void onFabShowSeveralClick(View view) {
        Intent intent = new Intent(view.getContext(), TransportChooserActivity.class);
        intent.putExtra(CURRENT_CITY_EXTRA, currentCity);
        startActivityForResult(intent, TRANSPORT_CHOOSER_REQUEST);
        fam.collapse();
    }

    public void onSettingsClick(View view) {
        Snackbar.make(view, "Settings", Snackbar.LENGTH_LONG).show();
        onBackPressed();
    }

    public void onChangeCityClick(View view) {
        startActivity(new Intent(this, CityChooserActivity.class));
        onBackPressed();
        finish();
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
        mapPresenter.setupMapCamera();
    }
}
