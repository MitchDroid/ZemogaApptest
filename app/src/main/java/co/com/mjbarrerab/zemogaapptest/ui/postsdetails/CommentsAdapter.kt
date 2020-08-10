package co.com.mjbarrerab.zemogaapptest.ui.postsdetails

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.com.mjbarrerab.zemogaapptest.R
import co.com.mjbarrerab.zemogaapptest.data.models.Comments
import co.com.mjbarrerab.zemogaapptest.data.models.Post
import co.com.mjbarrerab.zemogaapptest.ktextensions.inflate
import javax.inject.Inject

/**
 * Created by miller.barrera on 8/08/2020.
 */
class CommentsAdapter @Inject constructor() : RecyclerView.Adapter<CommentsViewHolder>() {

    lateinit var mContext: Context
    private var commentsList: MutableList<Comments> = ArrayList()

    private var removedPosition: Int = 0
    private lateinit var removedItem : Post

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        mContext = parent.context
        val inflatedView: View = parent.inflate(R.layout.default_list_item, false)
        return CommentsViewHolder(inflatedView)
    }

    override fun getItemCount() = commentsList.size


    fun getSelectedItem(position: Int): Comments {
        return commentsList[position]
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        val item = commentsList[position]
        holder.bindViews(item.body.toString())
    }

    fun populatePostsList(list: MutableList<Comments>) {
        commentsList = list
        notifyDataSetChanged()
    }

}