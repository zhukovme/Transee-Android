package in.transee.transee.presenter;

import android.support.design.widget.Snackbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import in.transee.transee.R;
import in.transee.transee.api.Repository;
import in.transee.transee.data.city.City;
import in.transee.transee.ui.ViewMvp;
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

    private ViewMvp view;
    private GoogleMap googleMap;
    private City currentCity;

    private PositionsDrawer positionsDrawer;

    private Subscription subscription = Subscriptions.empty();

    public MapPresenter(ViewMvp view, GoogleMap googleMap, City currentCity) {
        this.view = view;
        this.googleMap = googleMap;
        this.currentCity = currentCity;
        positionsDrawer = new PositionsDrawer(view.getContext(), googleMap);
    }

    public void setupCamera() {
        googleMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(currentCity.getCoordinates(), DEFAULT_MAP_ZOOM));
    }

    public void setupButtons() {
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
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
                .make(view.getView(), R.string.error_msg, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.action_retry, v -> locateTransports(transportIds))
                .show();
    }
}
