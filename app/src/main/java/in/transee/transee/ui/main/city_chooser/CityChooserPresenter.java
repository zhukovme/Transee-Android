package in.transee.transee.ui.main.city_chooser;

import javax.inject.Inject;

import in.transee.transee.R;
import in.transee.transee.api.Repository;
import in.transee.transee.ui.base.BasePresenter;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Zhukov Michael.
 */
public class CityChooserPresenter extends BasePresenter<CityChooserMvpView> {

    private final Repository repository;
    private Subscription subscription;

    @Inject
    public CityChooserPresenter(Repository repository) {
        this.repository = repository;
    }

    @Override
    public void detachView() {
        super.detachView();
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    public void loadCities() {
        getMvpView().showProgressBar();
        subscription = repository.getCities()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        cities -> {
                            if (cities.isEmpty()) {
                                getMvpView().showCitiesEmpty();
                                getMvpView().showError(R.string.empty_msg);
                            } else {
                                getMvpView().showCities(cities);
                            }
                        },
                        throwable -> {
                            getMvpView().showError(R.string.error_msg);
                            Timber.d(throwable, "Error while loading cities");
                        },
                        getMvpView()::hideProgressBar);
    }
}
