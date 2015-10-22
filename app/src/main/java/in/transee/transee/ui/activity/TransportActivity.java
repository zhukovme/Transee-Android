package in.transee.transee.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import in.transee.transee.Constants;
import in.transee.transee.R;
import in.transee.transee.model.city.City;

/**
 * @author Michael Zhukov
 */
public class TransportActivity extends AppCompatActivity implements OnMapReadyCallback {

    private City mCurrentCity;
    private DrawerLayout mDrawer;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport);

        mCurrentCity = (City) getIntent().getSerializableExtra(Constants.CURRENT_CITY_EXTRA);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(mCurrentCity.getName(this));
        setSupportActionBar(toolbar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        mDrawer.setDrawerShadow(R.drawable.drawer_dropshadow, GravityCompat.START);
        toggle.syncState();
    }

    public void onNavSettingsClick(View view) {
        Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    public void onChangeCityClick(View view) {
        startActivity(new Intent(this, CityChooserActivity.class));
        onBackPressed();
        finish();
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
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
        mMap = googleMap;
        LatLng cityCoordinates = new LatLng(
                mCurrentCity.getCoordinates().getLatitude(),
                mCurrentCity.getCoordinates().getLongitude()
        );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                cityCoordinates, Constants.DEFAULT_MAP_ZOOM));
    }
}
