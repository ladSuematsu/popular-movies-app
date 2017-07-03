package ladsoft.com.popularmoviesapp.core.mvp.presenter

import java.lang.ref.WeakReference

import ladsoft.com.popularmoviesapp.core.mvp.Mvp

abstract class MvpPresenter<V> : Mvp.Presenter<V> {

    private var _view: WeakReference<V>? = null

    override fun attachView(view: V) {
        this._view = WeakReference(view)
    }

    override fun detachView() {
        this._view = null
    }

    override val isViewAttached: Boolean
        get() = this._view != null && this._view!!.get() != null


    override fun getView(): V? {
        if (!isViewAttached) {
            throw IllegalStateException("Presenter is in detached state")
        }

        return _view?.get()
    }

}
