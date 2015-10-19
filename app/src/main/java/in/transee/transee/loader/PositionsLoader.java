package in.transee.transee.loader;

import android.content.Context;

import java.io.IOException;
import java.util.List;

import in.transee.transee.api.ApiFactory;
import in.transee.transee.api.response.RequestResult;
import in.transee.transee.api.response.Response;
import in.transee.transee.api.service.PositionsService;
import in.transee.transee.model.position.PositionType;
import retrofit.Call;

/**
 * @author Michael Zhukov
 */
public class PositionsLoader extends BaseLoader {

    private final String mCity;
    private final String[] mTypes;
    private final String[] mBuses;
    private final String[] mTrolleys;
    private final String[] mTrams;
    private final String[] mTaxi;

    public PositionsLoader(Context context, String city, String[] types, String[] buses,
                           String[] trolleys, String[] trams, String[] taxi) {
        super(context);
        mCity = city;
        mTypes = types;
        mBuses = buses;
        mTrolleys = trolleys;
        mTrams = trams;
        mTaxi = taxi;
    }

    @Override
    protected Response apiCall() throws IOException {
        PositionsService service = ApiFactory.getPositionsService();
        Call<List<PositionType>> call = service.positionTypes(mCity, mTypes, mBuses, mTrolleys,
                mTrams, mTaxi);
        List<PositionType> positionTypes = call.execute().body();
        return new Response()
                .setRequestResult(RequestResult.SUCCESS)
                .setAnswer(positionTypes);
    }
}
