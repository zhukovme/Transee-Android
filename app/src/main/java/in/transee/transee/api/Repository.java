package in.transee.transee.api;

import java.util.List;
import java.util.Map;

import in.transee.transee.data.city.City;
import in.transee.transee.data.route.RouteType;
import in.transee.transee.data.transport.TransportType;
import rx.Observable;

/**
 * @author Michael Zhukov
 */
public enum Repository {

    INSTANCE;

    public Observable<List<City>> getCities() {
//        if (database.hasData()) {
//            return database.data();
//        } else
        return Fetcher.INSTANCE.fetchCities();
    }

    public Observable<List<TransportType>> getTransports(String city) {
//        if (database.hasData()) {
//            return database.data();
//        } else
        return Fetcher.INSTANCE.fetchTransports(city);
    }

    public Observable<List<RouteType>> getRoutes(String city, Map<String, List<String>> filter) {
//        if (database.hasData()) {
//            return database.data();
//        } else
        return Fetcher.INSTANCE.fetchRoutes(city)
                .flatMap(Observable::from)
                .filter(route -> filter.containsKey(route.getType()))
                .flatMap(route -> Observable.just(route.getItems())
                        .flatMap(Observable::from)
                        .filter(item -> filter.get(route.getType()).contains(item.getId()))
                        .toList()
                        .map(items -> new RouteType(route.getType(), items)))
                .toList();
    }
}
