package in.transee.transee.loader;

import android.content.Context;

import java.io.IOException;
import java.util.List;

import in.transee.transee.api.ApiFactory;
import in.transee.transee.api.response.RequestResult;
import in.transee.transee.api.response.Response;
import in.transee.transee.api.response.RoutesResponse;
import in.transee.transee.api.service.RoutesService;
import in.transee.transee.model.route.RouteType;
import in.transee.transee.model.route.Routes;
import retrofit.Call;

/**
 * @author Michael Zhukov
 */
public class RoutesLoader extends BaseLoader {

    private final String mCity;

    public RoutesLoader(Context context, String city) {
        super(context);
        mCity = city;
    }

    @Override
    protected Response apiCall() throws IOException {
        RoutesService service = ApiFactory.getRoutesService();
        Call<List<RouteType>> call = service.routeTypes(mCity);
        List<RouteType> routeTypes = call.execute().body();
        return new RoutesResponse()
                .setRequestResult(RequestResult.SUCCESS)
                .setAnswer(new Routes(routeTypes));
    }
}
