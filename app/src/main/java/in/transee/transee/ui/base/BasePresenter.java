package in.transee.transee.ui.base;

/**
 * Created by Zhukov Michael.
 */
public abstract class BasePresenter<T extends  MvpView> implements Presenter<T> {

    private T mvpView;

    @Override
    public void attachView(T mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    public void detachView() {
        mvpView = null;
    }

    public boolean isViewAttached() {
        return mvpView != null;
    }

    public T getMvpView() {
        return mvpView;
    }

    public boolean checkViewAttached() {
        return isViewAttached();
    }
}
