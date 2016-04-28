package in.transee.transee.utils;

import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import java.util.HashMap;
import java.util.List;

import in.transee.transee.model.city.City;

/**
 * @author Michael Zhukov
 */
public class MapHelper {

    private static final float DEFAULT_MAP_ZOOM = 12;
    private static final float POLY_LINE_WIDTH = 6;

    private AppCompatActivity activity;
    private GoogleMap googleMap;
    private City currentCity;

    public MapHelper(AppCompatActivity activity, GoogleMap googleMap, City currentCity) {
        this.activity = activity;
        this.googleMap = googleMap;
        this.currentCity = currentCity;
    }

    public void setupMapCamera() {
        googleMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(currentCity.getCoordinates(), DEFAULT_MAP_ZOOM));
    }

    public void showSeveralTransport(HashMap<String, List<String>> transportIds) {

    }
}
