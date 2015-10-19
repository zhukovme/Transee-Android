package in.transee.transee.loader;

import android.content.Context;

import java.io.IOException;

import in.transee.transee.api.ApiFactory;
import in.transee.transee.api.response.RequestResult;
import in.transee.transee.api.response.Response;
import in.transee.transee.api.service.StationInfoService;
import in.transee.transee.model.station.info.StationInfo;
import retrofit.Call;

/**
 * @author Michael Zhukov
 */
public class StationInfoLoader extends BaseLoader {

    private final String mCity;
    private final String mId;

    public StationInfoLoader(Context context, String city, String id) {
        super(context);
        mCity = city;
        mId = id;
    }

    @Override
    protected Response apiCall() throws IOException {
        StationInfoService service = ApiFactory.getStationsInfoService();
        Call<StationInfo> call = service.stationInfo(mCity, mId);
        StationInfo stationInfo = call.execute().body();
        return new Response()
                .setRequestResult(RequestResult.SUCCESS)
                .setAnswer(stationInfo);
    }
}
