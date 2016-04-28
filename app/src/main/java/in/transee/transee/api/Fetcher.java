package in.transee.transee.api;

import java.util.List;

import in.transee.transee.model.city.City;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Michael Zhukov
 */
public enum Fetcher {

    INSTANCE;

    private ApiInterface apiInterface = ApiModule.getApiInterface();

    public Observable<List<City>> getCities() {
        return apiInterface.cities()
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::from)
                .flatMap(city ->
                        apiInterface.citiesCoordinates(city)
                                .map(coordinates -> new City(city, coordinates)))
                .toList()
                .observeOn(AndroidSchedulers.mainThread());
    }
}
