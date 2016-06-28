package in.transee.transee.ui.main.map;

import android.content.Context;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import in.transee.transee.data.position.PositionItem;
import in.transee.transee.data.position.Positions;
import in.transee.transee.data.position.Vehicle;
import in.transee.transee.data.route.RouteItem;
import in.transee.transee.data.route.Routes;
import in.transee.transee.injection.ActivityContext;
import in.transee.transee.util.ColorGenerator;
import in.transee.transee.util.TransportIconGenerator;

/**
 * Created by Zhukov Michael.
 */
public class GoogleMapHelper {

    private final Context context;
    private GoogleMap googleMap;
    private List<Marker> markerList;
    private TransportIconGenerator transportIconGenerator;
    private ColorGenerator colorGenerator = ColorGenerator.getInstance();

    @Inject
    public GoogleMapHelper(@ActivityContext Context context) {
        this.context = context;
        markerList = new ArrayList<>();
        transportIconGenerator = new TransportIconGenerator(context);
    }

    public void setGoogleMap(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    public void clear() {
        if (googleMap != null) {
            googleMap.clear();
        }
        markerList.clear();
    }

    public void moveCamera(LatLng coordinates, float zoom) {
        if (googleMap != null) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, zoom));
        }
    }

    public void drawRoutes(List<Routes> routesList) {
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

    public void drawPositions(List<Positions> positionsList) {
        if (!markerList.isEmpty()) {
            updatePositions(positionsList);
            return;
        }
        for (Positions positions : positionsList) {
            for (PositionItem item : positions.getItems()) {
                for (Vehicle vehicle : item.getVehicles()) {
                    Marker marker = drawMarker(
                            positions.getName(context) + " № " + item.getName(),
                            vehicle.getGosId(),
                            vehicle.getPosition(),
                            vehicle.getAngle(),
                            transportIconGenerator.getIcon(
                                    item.getName(),
                                    colorGenerator.fromString(item.getId() + positions.getType()),
                                    vehicle.getAngle()
                            ));
                    if (marker != null) {
                        markerList.add(marker);
                    }
                }
            }
        }
    }

    private void drawPolyline(PolylineOptions polylineOptions, LatLng latLng, int color) {
        if (googleMap != null) {
            googleMap.addPolyline(
                    polylineOptions
                            .add(latLng)
                            .width(6)
                            .color(color));
        }
    }

    private void updatePositions(List<Positions> positionsList) {
        for (Positions positions : positionsList) {
            for (PositionItem item : positions.getItems()) {
                for (Vehicle vehicle : item.getVehicles()) {
                    for (Marker marker : markerList) {
                        if (vehicle.getGosId().equals(marker.getSnippet())) {
                            if (!marker.getPosition().equals(vehicle.getPosition())) {
                                marker.setVisible(false);
                                marker.setPosition(vehicle.getPosition());
                                marker.setIcon(transportIconGenerator.getIcon(
                                        item.getName(),
                                        colorGenerator.fromString(item.getId() + positions.getType()),
                                        vehicle.getAngle())
                                );
                                marker.setRotation(vehicle.getAngle());
                                marker.setVisible(true);
                            }
                        }
                    }
                }
            }
        }
    }

    private Marker drawMarker(String title, String snippet, LatLng position, float rotation,
                              BitmapDescriptor icon) {
        if (googleMap == null) {
            return null;
        }
        return googleMap.addMarker(new MarkerOptions()
                .title(title)
                .snippet(snippet)
                .position(position)
                .rotation(rotation)
                .anchor(0.5f, 0.5f)
                .flat(true)
                .icon(icon)
                .alpha(0.9f));
    }
}
