package in.transee.transee.api.response;

import android.content.Context;
import android.support.annotation.Nullable;

/**
 * @author Michael Zhukov
 */
public class Response {

    @Nullable
    private Object mAnswer;

    private RequestResult mRequestResult;

    public Response() {
        mRequestResult = RequestResult.ERROR;
    }

    @Nullable
    public RequestResult getRequestResult() {
        return mRequestResult;
    }

    public Response setRequestResult(RequestResult requestResult) {
        mRequestResult = requestResult;
        return this;
    }

    public <T> T getTypedAnswer() {
        if (mAnswer == null) {
            return null;
        }
        return (T) mAnswer;
    }

    public Response setAnswer(@Nullable Object answer) {
        mAnswer = answer;
        return this;
    }

    public void save(Context context) {
    }
}
