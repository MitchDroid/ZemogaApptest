package co.com.mjbarrerab.zemogaapptest.ui.posts

import android.content.Context
import co.com.mjbarrerab.zemogaapptest.models.Post
import javax.inject.Inject

/**
 * Created by miller.barrera on 8/08/2020.
 */
class PostsListNavigator @Inject constructor()  : PostsListContract.Navigator {

    override fun goToItemDetails(selectedItem: Post, context: Context) {
        //val intent = Intent(context, RecipesDetailsActivity::class.java)
        //intent.putExtra("SELECTED_RECIPES", selectedItem)
        //context.startActivity(intent)
    }
}