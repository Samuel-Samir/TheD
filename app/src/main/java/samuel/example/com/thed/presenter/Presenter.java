package samuel.example.com.thed.presenter;

/**
 * Created by samuel on 5/31/2017.
 */

public interface Presenter<V> {

    void attachView(V view);

    void detachView();

}

