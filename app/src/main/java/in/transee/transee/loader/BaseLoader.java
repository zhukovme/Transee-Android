package in.transee.transee.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import in.transee.transee.api.response.RequestResult;
import in.transee.transee.api.response.Response;

/**
 * @author Michael Zhukov
 */
public abstract class BaseLoader extends AsyncTaskLoader<Response> {

    private static final Logger LOGGER = Logger.getLogger(BaseLoader.class.getName());

    public BaseLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
        LOGGER.log(Level.SEVERE, "Start loading " + getClass().getSimpleName());
    }

    @Override
    public Response loadInBackground() {
        try {
            Response response = apiCall();
            if (response.getRequestResult() == RequestResult.SUCCESS) {
                response.save(getContext());
                onSuccess();
            } else {
                onError();
            }
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return new Response();
        }
    }

    protected void onSuccess() {
        LOGGER.log(Level.SEVERE, "SUCCESS loading " + getClass().getSimpleName());
    }

    protected void onError() {
        LOGGER.log(Level.SEVERE, "ERROR loading " + getClass().getSimpleName());
    }

    protected abstract Response apiCall() throws IOException;
}
