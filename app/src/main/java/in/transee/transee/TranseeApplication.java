package in.transee.transee;

import android.app.Application;
import android.content.Context;

import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.YandexMetricaConfig;

import in.transee.transee.database.DatabaseHelperFactory;
import in.transee.transee.injection.component.ApplicationComponent;
import in.transee.transee.injection.component.DaggerApplicationComponent;
import in.transee.transee.injection.module.ApplicationModule;
import timber.log.Timber;

/**
 * @author Michael Zhukov
 */
public class TranseeApplication extends Application {

    ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseHelperFactory.setHelper(getApplicationContext());

        YandexMetricaConfig.Builder config = YandexMetricaConfig
                .newConfigBuilder(getString(R.string.app_metrica_api_key));

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            config.setLogEnabled();
        }

        YandexMetrica.activate(this, config.build());
        YandexMetrica.enableActivityAutoTracking(this);
    }

    @Override
    public void onTerminate() {
        DatabaseHelperFactory.releaseHelper();
        super.onTerminate();
    }

    public static TranseeApplication get(Context context) {
        return (TranseeApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (applicationComponent == null) {
            applicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return applicationComponent;
    }

    public void setComponent(ApplicationComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
    }
}
