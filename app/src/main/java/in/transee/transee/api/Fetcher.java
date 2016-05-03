package in.transee.transee.api;

import java.util.List;

import in.transee.transee.data.city.City;
import in.transee.transee.data.position.Positions;
import in.transee.transee.data.route.Routes;
import in.transee.transee.data.transport.Transports;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Michael Zhukov
 */
public class Fetcher {

    private static Fetcher instance = null;

    private ApiInterface apiInterface = ApiModule.getApiInterface();

    private Fetcher() {
    }

    public static Fetcher getInstance() {
        if (instance == null) {
            instance = new Fetcher();
        }
        return instance;
    }

    public Observable<List<City>> fetchCities() {
        return apiInterface.cities()
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::from)
                .flatMap(city ->
                        apiInterface.citiesCoordinates(city)
                                .map(coordinates -> new City(city, coordinates)))
                .toList();
    }

    public Observable<List<Transports>> fetchTransports(String city) {
        return apiInterface.transports(city)
                .subscribeOn(Schedulers.io());
    }

    public Observable<List<Routes>> fetchRoutes(String city) {
        return apiInterface.routes(city)
                .subscribeOn(Schedulers.io());
    }

    public Observable<List<Positions>> fetchPositions(String city, String[] types,
                                                      String[] buses, String[] trolleys,
                                                      String[] trams, String[] minibuses) {
        return apiInterface.positions(city, types, buses, trolleys, trams, minibuses)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
