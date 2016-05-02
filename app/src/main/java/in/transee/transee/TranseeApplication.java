package in.transee.transee;

import android.app.Application;

import in.transee.transee.database.DatabaseHelperFactory;

/**
 * @author Michael Zhukov
 */
public class TranseeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseHelperFactory.setHelper(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        DatabaseHelperFactory.releaseHelper();
        super.onTerminate();
    }
}
