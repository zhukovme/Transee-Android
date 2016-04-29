package in.transee.transee.api;

import java.util.List;

import in.transee.transee.data.city.City;
import in.transee.transee.data.transport.TransportType;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Michael Zhukov
 */
public enum Fetcher {

    INSTANCE;

    private ApiInterface apiInterface = ApiModule.getApiInterface();

    public Observable<List<City>> fetchCities() {
        return apiInterface.cities()
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::from)
                .flatMap(city ->
                        apiInterface.citiesCoordinates(city)
                                .map(coordinates -> new City(city, coordinates)))
                .toList()
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<TransportType>> fetchTransports(String city) {
        return apiInterface.transports(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
