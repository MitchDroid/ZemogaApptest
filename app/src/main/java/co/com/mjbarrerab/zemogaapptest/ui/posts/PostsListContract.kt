package co.com.mjbarrerab.zemogaapptest.ui.posts

import android.content.Context
import co.com.mjbarrerab.zemogaapptest.data.models.Post
import co.com.mjbarrerab.zemogaapptest.data.models.Users
import co.com.mjbarrerab.zemogaapptest.ui.base.MvpView

/**
 * Created by miller.barrera on 8/08/2020.
 */
interface PostsListContract {
    interface Presenter : MvpView.BasePresenter<View> {
        fun requestGetPosts()
        fun getPostFromDB()
        fun getUsers()
        fun updateIsNewPost(postId: Int?, isNewPost: Boolean)
        fun goToItemaDetailsActivity(selectedItem: Post, context: Context)
        fun displayErrorHandling(error: Throwable)
        fun deleteAllPostFromDB()
        fun deletePostById(postId: Int?)
    }

    interface View : MvpView.BaseView {
        companion object

        fun successPostsRequest(itemList: List<Post>)
        fun showErrorView(errorMessage: String?)
        fun showNoNetworkExceptionAlert(errorMessage: String?)
        fun deleteAllDBContentSuccess(itemList: List<Post>)
    }

    interface Navigator : MvpView.Navigator {
        fun goToPostDetailsView(selectedPostId: Int?, selectedUserId: Int?, context: Context)
    }
}