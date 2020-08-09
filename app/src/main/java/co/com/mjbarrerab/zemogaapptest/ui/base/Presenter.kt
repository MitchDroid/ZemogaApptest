package co.com.mjbarrerab.zemogaapptest.ui.base


/**
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the MvpView type that wants to be attached with.
 * Created by miller.barrera on 08/08/2020.
 */
interface Presenter<V : MvpView> {
    fun attachView(mvpView: V)
    fun detachView()
}