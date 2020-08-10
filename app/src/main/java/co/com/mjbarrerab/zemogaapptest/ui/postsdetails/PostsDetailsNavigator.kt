package co.com.mjbarrerab.zemogaapptest.ui.postsdetails

import android.content.Context
import co.com.mjbarrerab.zemogaapptest.data.models.Post
import javax.inject.Inject

/**
 * Created by miller.barrera on 8/08/2020.
 */
class PostsDetailsNavigator @Inject constructor()  : PostsDetailsContract.Navigator {

    override fun goToNextView(selectedItem: Post, context: Context) {
        //val intent = Intent(context, RecipesDetailsActivity::class.java)
        //intent.putExtra("SELECTED_RECIPES", selectedItem)
        //context.startActivity(intent)
    }
}