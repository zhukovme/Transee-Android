package in.transee.transee.api.response;

import android.content.Context;

import java.util.List;

import in.transee.transee.model.transport.TransportType;

/**
 * @author Michael Zhukov
 */
public class TransportResponse extends Response {

    @Override
    public void save(Context context) {
        List<TransportType> transportTypes = getTypedAnswer();
        if (transportTypes != null) {
            // save to database
        }
    }
}
