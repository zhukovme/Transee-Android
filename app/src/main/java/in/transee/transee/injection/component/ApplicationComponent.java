package in.transee.transee.injection.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import in.transee.transee.api.ApiInterface;
import in.transee.transee.api.Repository;
import in.transee.transee.injection.ApplicationContext;
import in.transee.transee.injection.module.ApplicationModule;

/**
 * Created by Zhukov Michael.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context context();
    Application application();
    ApiInterface apiInterface();
    Repository repository();
}
