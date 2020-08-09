package co.com.mjbarrerab.zemogaapptest.ui.base

/**
 * Created by miller.barrera on 08/08/2020.
 */
interface MvpView {

    interface Navigator

    interface BaseErrorView : BaseView

    interface BaseView : MvpView {
        fun showLoading(show: Boolean)
    }

    interface BasePresenter<T : MvpView> {
        fun attachView(mvpView: T)
        fun detachView()
    }

}