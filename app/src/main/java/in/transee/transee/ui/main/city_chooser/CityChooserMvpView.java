package in.transee.transee.ui.main.city_chooser;

import android.support.annotation.StringRes;

import java.util.List;

import in.transee.transee.data.city.City;
import in.transee.transee.ui.base.MvpView;

/**
 * Created by Zhukov Michael.
 */
public interface CityChooserMvpView extends MvpView {

    void showCities(List<City> cities);

    void showCitiesEmpty();

    void showError(@StringRes int message);

    void showProgressBar();

    void hideProgressBar();
}
