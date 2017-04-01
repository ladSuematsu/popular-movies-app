package ladsoft.com.popularmoviesapp.core.mvp.presenter;

import java.lang.ref.WeakReference;

import ladsoft.com.popularmoviesapp.core.mvp.Mvp;

public abstract class MvpPresenter<V> implements Mvp.Presenter<V> {

    private WeakReference<V> view;

    @Override
    public void attachView(V view) {
        this.view = new WeakReference<>(view);
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public boolean isViewAttached() {
        return this.view != null && this.view.get()!= null;
    }

    @Override
    public V getView() {
        if(!isViewAttached()) {
            throw new IllegalStateException("Presenter is in detached state");
        }

        return view.get();
    }

}
