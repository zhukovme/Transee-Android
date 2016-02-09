package in.transee.transee.loader;

import android.content.Context;

import java.io.IOException;
import java.util.List;

import in.transee.transee.api.ApiFactory;
import in.transee.transee.api.response.RequestResult;
import in.transee.transee.api.response.Response;
import in.transee.transee.api.response.TransportResponse;
import in.transee.transee.api.service.TransportService;
import in.transee.transee.model.transport.TransportType;
import retrofit.Call;

/**
 * @author Michael Zhukov
 */
public class TransportLoader extends BaseLoader {

    private final String mCity;

    public TransportLoader(Context context, String city) {
        super(context);
        mCity = city;
    }

    @Override
    protected Response apiCall() throws IOException {
        TransportService service = ApiFactory.getTransportService();
        Call<List<TransportType>> call = service.transportTypes(mCity);
        List<TransportType> transportTypes = call.execute().body();
        return new TransportResponse()
                .setRequestResult(RequestResult.SUCCESS)
                .setAnswer(transportTypes);
    }
}
