package co.com.mjbarrerab.zemogaapptest.ui.posts

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.com.mjbarrerab.zemogaapptest.R
import co.com.mjbarrerab.zemogaapptest.ktextensions.inflate
import co.com.mjbarrerab.zemogaapptest.models.Post
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

/**
 * Created by miller.barrera on 8/08/2020.
 */
class PostsListAdapter @Inject constructor() : RecyclerView.Adapter<PostsListViewHolder>() {

    lateinit var mContext: Context
    var postsList: MutableList<Post> = ArrayList()
    lateinit var mPostsListListener: PostsListListener
    private var removedPosition: Int = 0
    private lateinit var removedItem : Post

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsListViewHolder {
        mContext = parent.context
        val inflatedView: View = parent.inflate(R.layout.post_list_item, false)
        return PostsListViewHolder(inflatedView, mPostsListListener)
    }

    override fun getItemCount() = postsList.size


    fun getSelectedItem(position: Int): Post {
        return postsList[position]
    }

    override fun onBindViewHolder(holder: PostsListViewHolder, position: Int) {
        val item = postsList[position]
        holder.bindViews(item.id!!, item.title!!)
    }

    fun populatePostsList(list: MutableList<Post>) {
        postsList = list
        notifyDataSetChanged()
    }

    fun removeItem(viewHolder: PostsListViewHolder){
        removedPosition = viewHolder.adapterPosition
        removedItem = postsList[viewHolder.adapterPosition]
        postsList.removeAt(viewHolder.adapterPosition)
        notifyItemRemoved(viewHolder.adapterPosition)

        Snackbar.make(viewHolder.itemView, "Row ${removedItem.id} deleted", Snackbar.LENGTH_LONG)
            .setAction(mContext.getString(R.string.undo)){
                postsList.add(removedPosition, removedItem)
                notifyItemInserted(removedPosition)
            }.show()
    }
    fun setPostsListListener(recipesListListener: PostsListListener) {
        mPostsListListener = recipesListListener
    }
}