package in.transee.transee.injection.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import in.transee.transee.api.ApiInterface;
import in.transee.transee.api.ApiModule;
import in.transee.transee.database.DatabaseHelper;
import in.transee.transee.database.DatabaseHelperFactory;
import in.transee.transee.injection.ApplicationContext;

/**
 * Created by Zhukov Michael.
 */
@Module
public class ApplicationModule {

    protected final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    Application provideApplication() {
        return application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    ApiInterface provideApiInterface() {
        return ApiModule.getApiInterface();
    }

    @Provides
    @Singleton
    DatabaseHelper provideDatabaseHelper() {
        return DatabaseHelperFactory.getHelper();
    }
}
