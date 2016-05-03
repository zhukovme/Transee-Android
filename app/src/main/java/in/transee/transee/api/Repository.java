package in.transee.transee.api;

import java.util.List;
import java.util.Map;

import in.transee.transee.data.city.City;
import in.transee.transee.data.position.Positions;
import in.transee.transee.data.route.Routes;
import in.transee.transee.data.transport.Transports;
import in.transee.transee.database.DatabaseHelper;
import in.transee.transee.database.DatabaseHelperFactory;
import in.transee.transee.database.dao.CityDao;
import in.transee.transee.database.dao.TransportItemDao;
import in.transee.transee.database.dao.TransportsDao;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @author Michael Zhukov
 */
public enum Repository {

    INSTANCE;

    private DatabaseHelper database = DatabaseHelperFactory.getHelper();

    interface TransportType {
        String BUS = "autobus";
        String TROLLEY = "trolleybus";
        String TRAM = "tram";
        String MINIBUS = "minibus_taxi";
    }

    public Observable<List<City>> getCities() {
        CityDao cityDao = database.getCityDao();

        List<City> cities = cityDao.getAll();
        if (cities != null && !cities.isEmpty()) {
            return Observable.just(cities);
        }

        return Fetcher.INSTANCE.fetchCities()
                .doOnNext(cityDao::addInTx)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<Transports>> getTransports(String city) {
        TransportsDao transportsDao = database.getTransportsDao();
        TransportItemDao transportItemDao = database.getTransportItemDao();

        List<Transports> transportsList = transportsDao.getAll();

        if (transportsList != null && !transportsList.isEmpty()) {
            return Observable.just(transportsList);
        }

        return Fetcher.INSTANCE.fetchTransports(city)
                .flatMap(Observable::from)
                .doOnNext(transports -> {
                    transportsDao.add(transports);
                    transportItemDao.addInTx(transports.getItems(), transports);
                })
                .toList()
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<Routes>> getRoutes(String city, Map<String, List<String>> filter) {
//        if (database.hasData()) {
//            return database.data();
//        } else
        return Fetcher.INSTANCE.fetchRoutes(city)
                .flatMap(Observable::from)
                .filter(route -> filter.containsKey(route.getType()))
                .flatMap(route -> Observable
                        .from(route.getItems())
                        .filter(item -> filter.get(route.getType()).contains(item.getId()))
                        .toList()
                        .map(items -> new Routes(route.getType(), items)))
                .toList()
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<Positions>> getPositions(String city,
                                                    Map<String, List<String>> filter) {

        String[] types = filter.keySet().toArray(new String[filter.keySet().size()]);

        List<String> busesList = filter.get(TransportType.BUS);
        List<String> trolleysList = filter.get(TransportType.TROLLEY);
        List<String> tramsList = filter.get(TransportType.TRAM);
        List<String> minibusesList = filter.get(TransportType.MINIBUS);

        String[] buses = busesList != null ?
                busesList.toArray(new String[busesList.size()]) : null;

        String[] trolleys = trolleysList != null ?
                trolleysList.toArray(new String[trolleysList.size()]) : null;

        String[] trams = tramsList != null ?
                tramsList.toArray(new String[tramsList.size()]) : null;

        String[] minibuses = minibusesList != null ?
                minibusesList.toArray(new String[minibusesList.size()]) : null;

        return Fetcher.INSTANCE.fetchPositions(city, types, buses, trolleys, trams, minibuses);
    }
}
