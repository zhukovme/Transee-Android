package in.transee.transee.utils;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.HashMap;
import java.util.List;

import in.transee.transee.R;
import in.transee.transee.model.LatLon;
import in.transee.transee.model.city.City;
import in.transee.transee.model.position.PositionType;
import in.transee.transee.model.route.RouteItem;
import in.transee.transee.model.route.RouteType;
import in.transee.transee.model.route.Routes;

/**
 * @author Michael Zhukov
 */
public class MapHelper {

    private static final float DEFAULT_MAP_ZOOM = 12;
    private static final float POLY_LINE_WIDTH = 6;

    private AppCompatActivity mActivity;
    private GoogleMap mGoogleMap;
    private City mCurrentCity;

    private HashMap<String, List<String>> mTransportIds;

    public MapHelper(AppCompatActivity activity, GoogleMap googleMap, City currentCity) {
        mActivity = activity;
        mGoogleMap = googleMap;
        mCurrentCity = currentCity;
    }

    public void setupMapCamera() {
        LatLng cityCoordinates = new LatLng(
                mCurrentCity.getCoordinates().getLatitude(),
                mCurrentCity.getCoordinates().getLongitude()
        );
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cityCoordinates, DEFAULT_MAP_ZOOM));
    }

    public void showSeveralTransport(HashMap<String, List<String>> transportIds) {
        mGoogleMap.clear();
        mTransportIds = new HashMap<>(transportIds);
    }

    private void drawRoutes(Routes routes) {
        for (String transportType : mTransportIds.keySet()) {
            for (String transportId : mTransportIds.get(transportType)) {
                PolylineOptions polylineOptions = new PolylineOptions();
                for (RouteType routeType : routes.getRoutes()) {
                    if (transportType.equals(routeType.getType())) {
                        for (RouteItem routeItem : routeType.getItems()) {
                            if (transportId.equals(routeItem.getId())) {
                                for (LatLon latLon : routeItem.getRoute()) {
                                    addPolyline(polylineOptions, latLon, Color.RED);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void drawPositions(List<PositionType> positionTypes) {

    }

    private void addPolyline(PolylineOptions polylineOptions, LatLon latLon, int color) {
        mGoogleMap.addPolyline(polylineOptions
                .add(new LatLng(latLon.getLatitude(), latLon.getLongitude()))
                .color(color)
                .width(POLY_LINE_WIDTH));
    }
}
