package in.transee.transee.util.drawer;

import android.content.Context;
import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import in.transee.transee.data.position.PositionItem;
import in.transee.transee.data.position.Positions;
import in.transee.transee.data.position.Vehicle;
import in.transee.transee.util.TransportIconGenerator;

/**
 * @author Michael Zhukov
 */
public class PositionsDrawer {

    private final float DEFAULT_ANCHOR = 0.5f;
    private final float DEFAULT_ALPHA = 0.9f;

    private List<Marker> markerList;

    private Context context;
    private GoogleMap googleMap;
    private TransportIconGenerator transportIconGenerator;

    public PositionsDrawer(Context context, GoogleMap googleMap) {
        this.context = context;
        this.googleMap = googleMap;
        markerList = new ArrayList<>();
        transportIconGenerator = new TransportIconGenerator(context);
    }

    public List<Marker> getMarkerList() {
        return markerList;
    }

    public void draw(List<Positions> positionsList) {
        if (!markerList.isEmpty()) {
            update(positionsList);
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
                            transportIconGenerator.getIcon(item.getName(), Color.DKGRAY,
                                    vehicle.getAngle()));
                    markerList.add(marker);
                }
            }
        }
    }

    private void update(List<Positions> positionsList) {
        for (Positions positions : positionsList) {
            for (PositionItem item : positions.getItems()) {
                for (Vehicle vehicle : item.getVehicles()) {
                    for (Marker marker : markerList) {
                        if (vehicle.getGosId().equals(marker.getSnippet())) {
                            if (!marker.getPosition().equals(vehicle.getPosition())) {
                                marker.setVisible(false);
                                marker.setPosition(vehicle.getPosition());
                                marker.setIcon(transportIconGenerator.getIcon(item.getName(),
                                        Color.DKGRAY, vehicle.getAngle()));
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
        return googleMap.addMarker(new MarkerOptions()
                .title(title)
                .snippet(snippet)
                .position(position)
                .rotation(rotation)
                .anchor(DEFAULT_ANCHOR, DEFAULT_ANCHOR)
                .flat(true)
                .icon(icon)
                .alpha(DEFAULT_ALPHA));
    }
}
