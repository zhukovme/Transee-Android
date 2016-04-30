package in.transee.transee.api;

import java.util.List;
import java.util.Map;

import in.transee.transee.data.city.City;
import in.transee.transee.data.position.Positions;
import in.transee.transee.data.route.Routes;
import in.transee.transee.data.transport.Transports;
import rx.Observable;

/**
 * @author Michael Zhukov
 */
public enum Repository {

    INSTANCE;

    interface TransportType {
        String BUS = "autobus";
        String TROLLEY = "trolleybus";
        String TRAM = "tram";
        String MINIBUS = "minibus_taxi";
    }

    public Observable<List<City>> getCities() {
//        if (database.hasData()) {
//            return database.data();
//        } else
        return Fetcher.INSTANCE.fetchCities();
    }

    public Observable<List<Transports>> getTransports(String city) {
//        if (database.hasData()) {
//            return database.data();
//        } else
        return Fetcher.INSTANCE.fetchTransports(city);
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
                .toList();
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
