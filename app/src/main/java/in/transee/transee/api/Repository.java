package in.transee.transee.api;

import java.util.List;

import in.transee.transee.data.city.City;
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
}
