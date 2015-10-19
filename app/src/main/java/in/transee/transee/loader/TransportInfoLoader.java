package in.transee.transee.loader;

import android.content.Context;

import java.io.IOException;
import java.util.List;

import in.transee.transee.api.ApiFactory;
import in.transee.transee.api.response.RequestResult;
import in.transee.transee.api.response.Response;
import in.transee.transee.api.service.TransportInfoService;
import in.transee.transee.model.transport.info.TransportInfo;
import retrofit.Call;

/**
 * @author Michael Zhukov
 */
public class TransportInfoLoader extends BaseLoader {

    private final String mCity;
    private final String mType;
    private final String mGosId;

    public TransportInfoLoader(Context context, String city, String type, String gosId) {
        super(context);
        mCity = city;
        mType = type;
        mGosId = gosId;
    }

    @Override
    protected Response apiCall() throws IOException {
        TransportInfoService service = ApiFactory.getTransportsInfoService();
        Call<List<TransportInfo>> call = service.transportInfo(mCity, mType, mGosId);
        List<TransportInfo> transportInfo = call.execute().body();
        return new Response()
                .setRequestResult(RequestResult.SUCCESS)
                .setAnswer(transportInfo);
    }
}
