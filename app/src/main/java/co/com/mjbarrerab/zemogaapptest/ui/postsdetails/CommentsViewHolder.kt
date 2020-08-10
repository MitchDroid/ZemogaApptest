package co.com.mjbarrerab.zemogaapptest.ui.postsdetails

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.default_list_item.view.*

/**
 * Created by miller.barrera on 8/08/2020.
 */
class CommentsViewHolder(v: View) : RecyclerView.ViewHolder(v),
    View.OnClickListener {

    private var view: View = v

    init {
        view.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        //TODO Implements onclick behavior
    }

    fun bindViews(comments: String) {
        view.img_blue_circle.visibility = View.GONE
        view.tv_title.text = comments
    }
}