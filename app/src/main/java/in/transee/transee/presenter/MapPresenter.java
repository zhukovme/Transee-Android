package in.transee.transee.presenter;

import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import java.util.HashMap;
import java.util.List;

import in.transee.transee.api.Repository;
import in.transee.transee.data.city.City;
import in.transee.transee.view.drawer.RoutesDrawer;

/**
 * @author Michael Zhukov
 */
public class MapPresenter {

    private static final float DEFAULT_MAP_ZOOM = 12;

    private AppCompatActivity activity;
    private GoogleMap googleMap;
    private City currentCity;

    public MapPresenter(AppCompatActivity activity, GoogleMap googleMap, City currentCity) {
        this.activity = activity;
        this.googleMap = googleMap;
        this.currentCity = currentCity;
    }

    public void setupMapCamera() {
        googleMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(currentCity.getCoordinates(), DEFAULT_MAP_ZOOM));
    }

    public void locateTransports(HashMap<String, List<String>> transportIds) {
        googleMap.clear();
        RoutesDrawer routesDrawer = new RoutesDrawer(googleMap);
        Repository.INSTANCE
                .getRoutes(currentCity.getId(), transportIds)
                .subscribe(routesDrawer::draw);
    }
}
