package co.com.mjbarrerab.zemogaapptest.ui.posts

import android.content.Context
import co.com.mjbarrerab.zemogaapptest.models.Post
import co.com.mjbarrerab.zemogaapptest.ui.base.MvpView

/**
 * Created by miller.barrera on 8/08/2020.
 */
interface PostsListContract {
    interface Presenter : MvpView.BasePresenter<View> {
        fun requestGetPosts()
        fun goToItemaDetailsActivity(selectedItem: Post, context: Context)
        fun displayErrorHandling(error: Throwable)
    }

    interface View : MvpView.BaseView {
        companion object

        fun successPostsRequest(itemList: List<Post>)
        fun showErrorView()
        fun showNoNetworkExceptionAlert()
    }

    interface Navigator : MvpView.Navigator {
        fun goToItemDetails(selectedItem: Post, context: Context)

    }
}