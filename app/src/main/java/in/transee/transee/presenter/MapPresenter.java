package in.transee.transee.presenter;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import in.transee.transee.api.Repository;
import in.transee.transee.data.city.City;
import in.transee.transee.view.View;
import in.transee.transee.view.drawer.PositionsDrawer;
import in.transee.transee.view.drawer.RoutesDrawer;
import rx.Observable;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * @author Michael Zhukov
 */
public class MapPresenter {

    private static final float DEFAULT_MAP_ZOOM = 12;
    private static final int POSITIONS_UPDATE_PERIOD = 15;

    private View view;
    private GoogleMap googleMap;
    private City currentCity;

    private PositionsDrawer positionsDrawer;

    private Subscription subscription = Subscriptions.empty();

    public MapPresenter(View view, GoogleMap googleMap, City currentCity) {
        this.view = view;
        this.googleMap = googleMap;
        this.currentCity = currentCity;
        positionsDrawer = new PositionsDrawer(view.getContext(), googleMap);
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
                .subscribe(
                        routesDrawer::draw,
                        throwable -> {
                            view.onError();
                            throwable.printStackTrace();
                        });

        positionsDrawer.getMarkerList().clear();

        subscription = Observable.interval(0, POSITIONS_UPDATE_PERIOD, TimeUnit.SECONDS)
                .flatMap((tick) -> Repository.INSTANCE
                        .getPositions(currentCity.getId(), transportIds))
                .subscribe(
                        positionsDrawer::draw,
                        throwable -> {
                            view.onError();
                            throwable.printStackTrace();
                        });
    }

    public void onDestroy() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
