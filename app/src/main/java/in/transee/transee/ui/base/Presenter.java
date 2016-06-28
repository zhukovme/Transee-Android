package in.transee.transee.ui.base;

/**
 * Created by Zhukov Michael.
 */
public interface Presenter<T extends MvpView> {

    void attachView(T mvpView);
    void detachView();
}
