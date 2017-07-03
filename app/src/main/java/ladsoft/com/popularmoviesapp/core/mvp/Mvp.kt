package ladsoft.com.popularmoviesapp.core.mvp

interface Mvp {

    interface Presenter<V> {
        fun attachView(view: V)

        fun detachView()

        val isViewAttached: Boolean

        fun getView(): V?
    }

    interface Model<in T : Model.ModelCallback> {
        fun attach(callback: T)
        fun detach()
        val isAttached: Boolean

        interface ModelCallback
    }
}
