package co.com.mjbarrerab.zemogaapptest.ui.posts

import android.content.Context
import android.content.Intent
import co.com.mjbarrerab.zemogaapptest.data.models.Post
import co.com.mjbarrerab.zemogaapptest.ui.postsdetails.PostsDetailsActivity
import javax.inject.Inject

/**
 * Created by miller.barrera on 8/08/2020.
 */
class PostsListNavigator @Inject constructor()  : PostsListContract.Navigator {

    override fun goToPostDetailsView(selectedPostId: Int?, selectedUserId: Int?, context: Context) {
        val intent = Intent(context, PostsDetailsActivity::class.java)
        intent.putExtra("SELECTED_POST_ID", selectedPostId)
        intent.putExtra("SELECTED_USER_ID", selectedUserId)
        context.startActivity(intent)
    }
}