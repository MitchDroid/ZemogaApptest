package co.com.mjbarrerab.zemogaapptest.ui.base

/**
 * Base class that implements the Presenter interface and provide a base implementation for
 * attachView() and detachView(). It also handles keeping a reference to the mvpView that
 * can be accessed from the children classes by calling getMvpView().
 * Created by miller.barrera on 08/08/2020.
 */
open class BasePresenter<T : MvpView> : Presenter<T> {

    private var mMvpView: T? = null

    override fun attachView(mvpView: T) {
        this.mMvpView = mvpView
    }

    override fun detachView() {
        mMvpView = null
    }

    fun isViewAttached(): Boolean {
        return mMvpView != null
    }

    fun getMvpView(): T? {
        return mMvpView
    }

    fun checkViewAttached() {
        if (!isViewAttached()) throw MvpViewNotAttachedException()
    }

    class MvpViewNotAttachedException : RuntimeException("Please call Presenter.attachView(MvpView) before" + " requesting network to the Presenter")

}