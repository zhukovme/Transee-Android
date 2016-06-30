package in.transee.transee.ui.main.map;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import in.transee.transee.R;
import in.transee.transee.api.Repository;
import in.transee.transee.data.favorite.Favorite;
import in.transee.transee.ui.base.BasePresenter;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * @author Michael Zhukov
 */
public class MapPresenter extends BasePresenter<MapMvpView> {

    private final Repository repository;
    private CompositeSubscription subscriptions;

    private String currentCity;
    private Map<String, List<String>> transportIds;
    private String type;
    private String gosId;
    private String itemId;

    @Inject
    public MapPresenter(Repository repository) {
        this.repository = repository;
        subscriptions = new CompositeSubscription();
    }

    @Override
    public void detachView() {
        super.detachView();
        unsubscribe();
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public void setTransportIds(Map<String, List<String>> transportIds) {
        this.transportIds = transportIds;
    }

    public void loadTransport() {
        getMvpView().clearMap();
        unsubscribe();
        subscriptions = new CompositeSubscription();

        Subscription routesSubscription = repository
                .getRoutes(currentCity, transportIds)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        getMvpView()::showTransportRoutes,
                        throwable -> {
                            getMvpView().showErrorTransportPositions(R.string.error_msg);
                            Timber.d(throwable, "Error while loading routes");
                        });

        Subscription positionsSubscription = Observable
                .interval(0, 15, TimeUnit.SECONDS)
                .flatMap(tick -> repository
                        .getPositions(currentCity, transportIds))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        getMvpView()::showTransportPositions,
                        throwable -> {
                            getMvpView().showErrorTransportPositions(R.string.error_msg);
                            Timber.d(throwable, "Error while loading positions");
                        });

        subscriptions.add(routesSubscription);
        subscriptions.add(positionsSubscription);
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setGosId(String gosId) {
        this.gosId = gosId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void loadTransportInfo() {
        getMvpView().showTransportInfoProgressBar();
        Subscription transportInfoSubscription = repository
                .getTransportInfo(currentCity, type, gosId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        transportInfoList -> {
                            if (transportInfoList.isEmpty()) {
                                getMvpView().showTransportInfoEmpty();
                            } else {
                                getMvpView().showTransportInfo(transportInfoList);
                                getMvpView().hideTransportInfoProgressBar();
                            }
                        },
                        throwable -> {
                            getMvpView().showErrorTransportInfo(R.string.error_msg);
                            Timber.d(throwable, "Error while loading transport info");
                        });
        subscriptions.add(transportInfoSubscription);
    }

    public void loadFavorites() {
        Subscription favoritesSubscription = repository
                .getFavorites(currentCity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        getMvpView()::showFavorites,
                        throwable -> Timber.d(throwable, "Error while loading favorites"));
        subscriptions.add(favoritesSubscription);
    }

    public boolean isFavorite(String name) {
        return repository.isFavorite(currentCity, name);
    }

    public void addToFavorite(String name) {
        repository.addToFavorite(new Favorite(currentCity, name, type, itemId));
    }

    public void deleteFromFavorite(String name) {
        repository.deleteFromFavorite(currentCity, name);
    }

    private void unsubscribe() {
        if (subscriptions != null) {
            subscriptions.clear();
        }
    }
}
