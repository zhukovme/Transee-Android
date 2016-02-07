package in.transee.transee.utils;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import in.transee.transee.model.city.City;

/**
 * @author Michael Zhukov
 */
public class MapHelper {

    public static final float DEFAULT_MAP_ZOOM = 12;

    private GoogleMap mGoogleMap;

    public MapHelper(GoogleMap googleMap) {
        mGoogleMap = googleMap;
    }

    public void setupMapCamera(City currentCity) {
        LatLng cityCoordinates = new LatLng(
                currentCity.getCoordinates().getLatitude(),
                currentCity.getCoordinates().getLongitude()
        );
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cityCoordinates, DEFAULT_MAP_ZOOM));
    }
}
