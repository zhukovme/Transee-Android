package in.transee.transee.loader;

import android.content.Context;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import in.transee.transee.api.ApiFactory;
import in.transee.transee.api.response.RequestResult;
import in.transee.transee.api.response.Response;
import in.transee.transee.api.service.CitiesService;
import in.transee.transee.model.LatLon;
import in.transee.transee.model.city.City;
import retrofit.Call;

/**
 * @author Michael Zhukov
 */
public class CitiesLoader extends BaseLoader {

    public CitiesLoader(Context context) {
        super(context);
    }

    @Override
    protected Response apiCall() throws IOException {
        CitiesService service = ApiFactory.getCitiesService();
        List<City> cities = new LinkedList<>();

        Call<List<String>> citiesCall = service.cities();
        List<String> citiesAnswer = citiesCall.execute().body();

        for (String city : citiesAnswer) {
            Call<double[]> coordinatesCall = service.coordinates(city);
            double[] coordinates = coordinatesCall.execute().body();

            cities.add(new City(city, new LatLon(coordinates[0], coordinates[1])));
        }
        return new Response()
                .setRequestResult(RequestResult.SUCCESS)
                .setAnswer(cities);
    }
}
