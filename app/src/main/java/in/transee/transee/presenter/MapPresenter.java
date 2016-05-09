package in.transee.transee.presenter;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import in.transee.transee.R;
import in.transee.transee.api.Repository;
import in.transee.transee.data.city.City;
import in.transee.transee.ui.activity.MapActivity;
import in.transee.transee.util.drawer.PositionsDrawer;
import in.transee.transee.util.drawer.RoutesDrawer;
import rx.Observable;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * @author Michael Zhukov
 */
public class MapPresenter {

    private static final float DEFAULT_MAP_ZOOM = 12;
    private static final int POSITIONS_UPDATE_PERIOD = 15;

    private MapActivity activity;
    private GoogleMap googleMap;
    private City currentCity;

    private PositionsDrawer positionsDrawer;

    private Subscription subscription = Subscriptions.empty();

    public MapPresenter(MapActivity activity, GoogleMap googleMap, City currentCity) {
        this.activity = activity;
        this.googleMap = googleMap;
        this.currentCity = currentCity;
        positionsDrawer = new PositionsDrawer(activity, googleMap);
    }

    public void setupCamera() {
        googleMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(currentCity.getCoordinates(), DEFAULT_MAP_ZOOM));
    }

    public void setupButtons() {
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
    }

    public void setupInfoWindowAdapter(BottomSheetBehavior bsTransportInfoBehavior,
                                       FloatingActionButton fabMapMain) {

        bsTransportInfoBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_EXPANDED:
                        fabMapMain
                                .setImageDrawable(ContextCompat.getDrawable(activity,
                                        R.drawable.ic_star_outline_white_24dp));
                        fabMapMain.setOnClickListener(v -> onFabAddToFavoriteClick(v));
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        fabMapMain
                                .setImageDrawable(ContextCompat.getDrawable(activity,
                                        R.drawable.ic_map_marker_multiple_white_24dp));
                        fabMapMain.setOnClickListener(activity::onFabChooseTransportClick);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        googleMap.setOnMarkerClickListener(marker -> {
            marker.showInfoWindow();
            bsTransportInfoBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            return true;
        });
    }

    public void onFabAddToFavoriteClick(View view) {
        // // TODO: 5/9/2016 add to favorite
    }

    public void locateTransports(HashMap<String, List<String>> transportIds) {
        clearAndUnsubscribe();
        loadAndDrawRoutes(transportIds);
        loadAndDrawPositions(transportIds);
    }

    public void clearAndUnsubscribe() {
        googleMap.clear();
        positionsDrawer.getMarkerList().clear();
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    private void loadAndDrawRoutes(HashMap<String, List<String>> transportIds) {
        RoutesDrawer routesDrawer = new RoutesDrawer(googleMap);
        Repository
                .getInstance()
                .getRoutes(currentCity.getId(), transportIds)
                .subscribe(
                        routesDrawer::draw,
                        throwable -> {
                            onError(transportIds);
                            throwable.printStackTrace();
                        });
    }

    private void loadAndDrawPositions(HashMap<String, List<String>> transportIds) {
        subscription = Observable
                .interval(0, POSITIONS_UPDATE_PERIOD, TimeUnit.SECONDS)
                .flatMap((tick) -> Repository
                        .getInstance()
                        .getPositions(currentCity.getId(), transportIds))
                .subscribe(
                        positionsDrawer::draw,
                        throwable -> {
                            onError(transportIds);
                            throwable.printStackTrace();
                        });
    }

    private void onError(HashMap<String, List<String>> transportIds) {
        Snackbar
                .make(activity.getView(), R.string.error_msg, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.action_retry, v -> locateTransports(transportIds))
                .show();
    }
}
