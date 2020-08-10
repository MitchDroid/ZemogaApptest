package co.com.mjbarrerab.zemogaapptest.ui.postsdetails

import android.content.Context
import co.com.mjbarrerab.zemogaapptest.data.models.Comments
import co.com.mjbarrerab.zemogaapptest.data.models.Post
import co.com.mjbarrerab.zemogaapptest.data.models.Users
import co.com.mjbarrerab.zemogaapptest.ui.base.MvpView

/**
 * Created by miller.barrera on 8/08/2020.
 */
interface PostsDetailsContract {
    interface Presenter : MvpView.BasePresenter<View> {
        fun getPostDetailsByIdFromDB(postId: Int)
        fun displayErrorHandling(error: Throwable)
        fun getComments(postId: Int)
        fun getUserByIdFromDB(userId: Int)
        fun saveAsFavorite(postId: Int, isFavorite: Boolean)
    }

    interface View : MvpView.BaseView {
        companion object

        fun successPostsByIdRequest(post: Post)
        fun successCommentsRequest(list: List<Comments>)
        fun successUserByIdRequest(user: Users)
        fun successFavoriteAdded()
        fun showErrorView(errorMessage: String?)
        fun showNoNetworkExceptionAlert(errorMessage: String?)
    }

    interface Navigator : MvpView.Navigator {
        //TODO implement navigator
        fun goToNextView(selectedItem: Post, context: Context)

    }
}