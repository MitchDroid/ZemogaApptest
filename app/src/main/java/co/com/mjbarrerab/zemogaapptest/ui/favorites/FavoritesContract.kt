package co.com.mjbarrerab.zemogaapptest.ui.favorites

import android.content.Context
import co.com.mjbarrerab.zemogaapptest.data.models.Post
import co.com.mjbarrerab.zemogaapptest.data.models.Users
import co.com.mjbarrerab.zemogaapptest.ui.base.MvpView

/**
 * Created by miller.barrera on 8/08/2020.
 */
interface FavoritesContract {
    interface Presenter : MvpView.BasePresenter<View> {
        fun getFavoritesFromDB()
        fun displayErrorHandling(error: Throwable)
        fun deleteAllPostFromDB()
    }

    interface View : MvpView.BaseView {
        companion object

        fun successFavoritesFromDB(itemList: List<Post>)
        fun showErrorView(errorMessage: String?)
        fun showNoNetworkExceptionAlert(errorMessage: String?)
        fun deleteAllDBContentSuccess(itemList: List<Post>)
    }

    interface Navigator : MvpView.Navigator {
        fun goToPostDetailsView(selectedPostId: Int?, selectedUserId: Int?, context: Context)
    }
}