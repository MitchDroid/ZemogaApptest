package co.com.mjbarrerab.zemogaapptest.ui.posts

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.default_list_item.view.*

/**
 * Created by miller.barrera on 8/08/2020.
 */
class PostsListViewHolder(v: View, postsListListener: PostsListListener) : RecyclerView.ViewHolder(v),
    View.OnClickListener {

    private var view: View = v
    private var mRecipesListListener: PostsListListener = postsListListener

    init {
        view.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        mRecipesListListener.showSelectedPostDetails(adapterPosition)
    }

    fun bindViews(id: Int, title: String, isNewPost: Boolean) {
        if (isNewPost)
            view.img_blue_circle.visibility = View.VISIBLE
        else
            view.img_blue_circle.visibility = View.GONE

        view.tv_title.text = title
    }
}