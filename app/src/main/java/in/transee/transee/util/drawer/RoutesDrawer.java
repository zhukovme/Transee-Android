package in.transee.transee.util.drawer;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import in.transee.transee.data.route.RouteItem;
import in.transee.transee.data.route.Routes;
import in.transee.transee.util.ColorGenerator;

/**
 * @author Michael Zhukov
 */
public class RoutesDrawer {

    private static final float POLY_LINE_WIDTH = 6;

    private GoogleMap googleMap;

    private ColorGenerator colorGenerator = ColorGenerator.getInstance();

    public RoutesDrawer(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    public void draw(List<Routes> routesList) {
        for (Routes routes : routesList) {
            for (RouteItem item : routes.getItems()) {
                PolylineOptions polylineOptions = new PolylineOptions();
                for (LatLng latLng : item.getRoute()) {
                    drawPolyline(polylineOptions, latLng,
                            colorGenerator.fromString(item.getId() + routes.getType()));
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
