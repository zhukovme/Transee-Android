package in.transee.transee.api;

import java.util.List;

import in.transee.transee.model.city.City;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
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
                .flatMap(new Func1<List<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(List<String> cities) {
                        return Observable.from(cities);
                    }
                })
                .flatMap(new Func1<String, Observable<City>>() {
                    @Override
                    public Observable<City> call(final String city) {
                        return apiInterface.citiesCoordinates(city)
                                .map(new Func1<double[], City>() {
                                    @Override
                                    public City call(double[] coordinates) {
                                        return new City(city, coordinates);
                                    }
                                });
                    }
                })
                .toList()
                .observeOn(AndroidSchedulers.mainThread());
    }
}
