package in.transee.transee.api;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import in.transee.transee.data.city.City;
import in.transee.transee.data.favorite.Favorite;
import in.transee.transee.data.position.Positions;
import in.transee.transee.data.route.Routes;
import in.transee.transee.data.transport.Transports;
import in.transee.transee.data.transport.info.TransportInfo;
import in.transee.transee.database.DatabaseHelper;
import in.transee.transee.database.dao.CityDao;
import in.transee.transee.database.dao.FavoriteDao;
import in.transee.transee.database.dao.TransportItemDao;
import in.transee.transee.database.dao.TransportsDao;
import rx.Observable;

/**
 * @author Michael Zhukov
 */
public class Repository {

    private final ApiInterface apiInterface;
    private CityDao cityDao;
    private TransportsDao transportsDao;
    private TransportItemDao transportItemDao;
    private FavoriteDao favoriteDao;

    @Inject
    public Repository(ApiInterface apiInterface, DatabaseHelper database) {
        this.apiInterface = apiInterface;
        this.cityDao = database.getCityDao();
        this.transportsDao = database.getTransportsDao();
        this.transportItemDao = database.getTransportItemDao();
        this.favoriteDao = database.getFavoriteDao();
    }

    public interface TransportType {
        String BUS = "autobus";
        String TROLLEY = "trolleybus";
        String TRAM = "tram";
        String MINIBUS = "minibus_taxi";
    }

    public Observable<List<City>> getCities() {
        List<City> cities = cityDao.getAll();
        if (cities != null && !cities.isEmpty()) {
            return Observable.just(cities);
        }
        return apiInterface.cities()
                .flatMap(Observable::from)
                .flatMap(city -> apiInterface
                        .citiesCoordinates(city)
                        .map(coordinates -> new City(city, coordinates)))
                .toList()
                .doOnNext(cityDao::addInTx);
    }

    public Observable<List<Transports>> getTransports(String city) {
        List<Transports> transportsList = transportsDao.getAll(city);

        if (transportsList != null && !transportsList.isEmpty()) {
            return Observable.just(transportsList);
        }

        return apiInterface.transports(city)
                .flatMap(Observable::from)
                .doOnNext(transports -> {
                    transports.setCity(city);
                    transportsDao.add(transports);
                    transportItemDao.addInTx(transports.getItems(), transports);
                })
                .toList();
    }

    public Observable<List<Routes>> getRoutes(String city, Map<String, List<String>> filter) {
//        RoutesDao routesDao = database.getRoutesDao();
//        RouteItemDao routeItemDao = database.getRouteItemDao();
//
//        List<Routes> routesList = routesDao.getAll();
//
//        if (routesList != null && !routesList.isEmpty()) {
//            return Observable
//                    .from(routesList)
//                    .filter(route -> filter.containsKey(route.getType()))
//                    .flatMap(route -> Observable
//                            .from(route.getItems())
//                            .filter(item -> filter.get(route.getType()).contains(item.getId()))
//                            .toList()
//                            .map(items -> new Routes(route.getType(), items)))
//                    .toList();
//        } // TODO: 5/3/2016 build query for this
        return apiInterface.routes(city)
                .flatMap(Observable::from)
//                .doOnNext(routes -> {
//                    routes.setCity(city);
//                    routesDao.add(routes);
//                    routeItemDao.addInTx(routes.getItems(), routes);
//                })
                .filter(route -> filter.containsKey(route.getType()))
                .flatMap(route -> Observable
                        .from(route.getItems())
                        .filter(item -> filter.get(route.getType()).contains(item.getId()))
                        .toList()
                        .map(items -> new Routes(route.getType(), items)))
                .toList();
    }

    public Observable<List<Positions>> getPositions(String city, Map<String, List<String>> filter) {

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

        return apiInterface.positions(city, types, buses, trolleys, trams, minibuses);
    }

    public Observable<List<TransportInfo>> getTransportInfo(String city, String type, String gosId) {
        return apiInterface.transportInfo(city, type, gosId);
    }

    public Observable<List<Favorite>> getFavorites(String city) {
        List<Favorite> favorites = favoriteDao.getAll(city);

        if (favorites != null && !favorites.isEmpty()) {
            return Observable
                    .just(favorites);
        }
        return Observable.empty();
    }

    public boolean isFavorite(String city, String name) {
        return favoriteDao.isFavorite(city, name);
    }

    public void addToFavorite(Favorite favorite) {
        favoriteDao.add(favorite);
    }

    public void deleteFromFavorite(String city, String name) {
        favoriteDao.deleteItem(city, name);
    }

    public void deleteAllFromFavorite() {
        favoriteDao.deleteAll();
    }
}
