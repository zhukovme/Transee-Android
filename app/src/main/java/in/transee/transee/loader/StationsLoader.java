package in.transee.transee.loader;

import android.content.Context;

import java.io.IOException;
import java.util.List;

import in.transee.transee.api.ApiFactory;
import in.transee.transee.api.response.RequestResult;
import in.transee.transee.api.response.Response;
import in.transee.transee.api.response.StationsResponse;
import in.transee.transee.api.service.StationsService;
import in.transee.transee.model.station.Station;
import retrofit.Call;

/**
 * @author Michael Zhukov
 */
public class StationsLoader extends BaseLoader {

    private final String mCity;

    public StationsLoader(Context context, String city) {
        super(context);
        mCity = city;
    }

    @Override
    protected Response apiCall() throws IOException {
        StationsService service = ApiFactory.getStationsService();
        Call<List<Station>> call = service.stations(mCity);
        List<Station> stations = call.execute().body();
        return new StationsResponse()
                .setRequestResult(RequestResult.SUCCESS)
                .setAnswer(stations);
    }
}
