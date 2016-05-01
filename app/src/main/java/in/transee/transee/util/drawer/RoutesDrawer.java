package in.transee.transee.util.drawer;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import in.transee.transee.data.route.RouteItem;
import in.transee.transee.data.route.Routes;

/**
 * @author Michael Zhukov
 */
public class RoutesDrawer {

    private static final float POLY_LINE_WIDTH = 6;

    private GoogleMap googleMap;

    public RoutesDrawer(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    public void draw(List<Routes> routesList) {
        for (Routes routes : routesList) {
            for (RouteItem item : routes.getItems()) {
                PolylineOptions polylineOptions = new PolylineOptions();
                for (LatLng latLng : item.getRoute()) {
                    drawPolyline(polylineOptions, latLng, Color.RED);
                }
            }
        }
    }

    private void drawPolyline(PolylineOptions polylineOptions, LatLng latLng, int color) {
        googleMap.addPolyline(
                polylineOptions
                        .add(latLng)
                        .width(POLY_LINE_WIDTH)
                        .color(color));
    }
}
