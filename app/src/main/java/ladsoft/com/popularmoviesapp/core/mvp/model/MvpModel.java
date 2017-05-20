package ladsoft.com.popularmoviesapp.core.mvp.model;


import java.lang.ref.WeakReference;

import ladsoft.com.popularmoviesapp.core.mvp.Mvp;

public abstract class MvpModel<T extends Mvp.Model.ModelCallback> implements Mvp.Model<T> {
    private WeakReference<T> callback;

    @Override
    public void attach(T callback) {
        this.callback = new WeakReference<>(callback);
    }

    @Override
    public void detach() {
        this.callback = null;
    }

    @Override
    public boolean isAttached() {
        return this.callback != null && this.callback.get()!= null;
    }

    public T getCallback() throws IllegalStateException {
        if(!isAttached()) {
            throw new IllegalStateException("Model is in detached state");
        }

        return callback.get();
    }

}
