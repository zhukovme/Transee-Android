package in.transee.transee.ui.main.map;

import android.support.annotation.StringRes;

import java.util.List;

import in.transee.transee.data.position.Positions;
import in.transee.transee.data.route.Routes;
import in.transee.transee.ui.base.MvpView;

/**
 * Created by Zhukov Michael.
 */
public interface MapMvpView extends MvpView {

    void showTransportRoutes(List<Routes> routesList);

    void showTransportPositions(List<Positions> positionsList);

    void showError(@StringRes int message);

    void showProgressBar();

    void hideProgressBar();
}
