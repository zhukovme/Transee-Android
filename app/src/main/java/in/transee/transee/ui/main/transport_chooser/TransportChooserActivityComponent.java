package in.transee.transee.ui.main.transport_chooser;

import dagger.Component;
import in.transee.transee.injection.PerActivity;
import in.transee.transee.injection.component.ApplicationComponent;
import in.transee.transee.injection.component.BaseActivityComponent;
import in.transee.transee.injection.module.ActivityModule;

/**
 * Created by Zhukov Michael.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface TransportChooserActivityComponent extends BaseActivityComponent {
    void inject(TransportChooserActivity transportChooserActivity);
}

