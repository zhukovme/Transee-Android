package in.transee.transee.api;

import java.util.List;

import in.transee.transee.model.city.City;
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
        return Fetcher.INSTANCE.getCities();
    }
}
