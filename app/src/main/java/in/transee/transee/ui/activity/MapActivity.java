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

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.HashMap;
import java.util.List;

import in.transee.transee.R;
import in.transee.transee.model.city.City;
import in.transee.transee.utils.MapHelper;

/**
 * @author Michael Zhukov
 */
public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final String CURRENT_CITY_EXTRA = "current_city_extra";

    public static final int TRANSPORT_CHOOSER_REQUEST = 1;

    private DrawerLayout mDrawer;

    private City mCurrentCity;
    private MapHelper mMapHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mCurrentCity = (City) getIntent().getSerializableExtra(CURRENT_CITY_EXTRA);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final FloatingActionsMenu fam = (FloatingActionsMenu) findViewById(R.id.fam);
        FloatingActionButton fabShowSeveral = (FloatingActionButton) findViewById(R.id.fab_show_several);

        toolbar.setTitle(mCurrentCity.getName(this));
        setSupportActionBar(toolbar);

        mapFragment.getMapAsync(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        mDrawer.setDrawerShadow(R.drawable.drawer_dropshadow, GravityCompat.START);
        toggle.syncState();

        fabShowSeveral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TransportChooserActivity.class);
                intent.putExtra(CURRENT_CITY_EXTRA, mCurrentCity);
                startActivityForResult(intent, TRANSPORT_CHOOSER_REQUEST);
                fam.collapse();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TRANSPORT_CHOOSER_REQUEST:
                if (resultCode == RESULT_OK) {
                    HashMap<String, List<String>> selectedTransport =
                            (HashMap<String, List<String>>) data
                                    .getSerializableExtra(TransportChooserActivity.SELECTED_TRANSPORT_EXTRA);
                    mMapHelper.showSeveralTransport(selectedTransport);
                }
        }
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
        mMapHelper = new MapHelper(this, googleMap, mCurrentCity);
        mMapHelper.setupMapCamera();
    }
}
