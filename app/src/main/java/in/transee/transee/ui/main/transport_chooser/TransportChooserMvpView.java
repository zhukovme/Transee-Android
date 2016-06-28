package in.transee.transee.ui.main.transport_chooser;

import android.support.annotation.StringRes;

import java.util.List;

import in.transee.transee.data.transport.Transports;
import in.transee.transee.ui.base.MvpView;

/**
 * Created by Zhukov Michael.
 */
public interface TransportChooserMvpView extends MvpView {

    void showTransports(List<Transports> transportsList);

    void showTransportsEmpty();

    void showError(@StringRes int message);

    void showProgressBar();

    void hideProgressBar();
}
