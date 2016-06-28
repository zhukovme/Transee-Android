package in.transee.transee.ui.main.transport_chooser;

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
public class TransportChooserPresenter extends BasePresenter<TransportChooserMvpView> {

    private final Repository repository;
    private String currentCity;
    private Subscription subscription;

    @Inject
    public TransportChooserPresenter(Repository repository) {
        this.repository = repository;
    }

    @Override
    public void detachView() {
        super.detachView();
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public void loadTransports() {
        getMvpView().showProgressBar();
        subscription = repository.getTransports(currentCity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        transportsList -> {
                            if (transportsList.isEmpty()) {
                                getMvpView().showTransportsEmpty();
                                getMvpView().showError(R.string.empty_msg);
                            } else {
                                getMvpView().showTransports(transportsList);
                            }
                        },
                        throwable -> {
                            Timber.d(throwable, "Error while loading transports");
                            getMvpView().showError(R.string.error_msg);
                        },
                        getMvpView()::hideProgressBar);
    }
}
