package ladsoft.com.popularmoviesapp.core.mvp;

public interface Mvp {

        interface Presenter<V> {
                void attachView(V view);

                void detachView();

                boolean isViewAttached();

                V getView();
        }

        interface Model<T extends Model.ModelCallback> {
                void attach(T callback);
                void detach();
                boolean isAttached();

                interface ModelCallback {}
        }
}
