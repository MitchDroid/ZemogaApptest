package co.com.mjbarrerab.zemogaapptest.ui.posts

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.com.mjbarrerab.zemogaapptest.R
import co.com.mjbarrerab.zemogaapptest.data.models.Post
import co.com.mjbarrerab.zemogaapptest.ui.base.activity.BaseMVPActivity
import kotlinx.android.synthetic.main.activity_posts.*
import kotlinx.android.synthetic.main.activity_posts_content.*
import javax.inject.Inject

class PostsListActivity : BaseMVPActivity(), PostsListContract.View, PostsListListener, View.OnClickListener {

    @Inject
    lateinit var postsListPresenter: PostsListPresenter
    @Inject
    lateinit var postsListAdapter: PostsListAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var deleteIcon: Drawable
    var mutableList: MutableList<Post> = mutableListOf()
    private var swipeBackground: ColorDrawable = ColorDrawable(Color.parseColor("#FF0000"))

    override fun getLayout(): Int {
        return R.layout.activity_posts
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)
        injectDependency()
        setSupportActionBar(toolbar)
        setupRecyclerView()
        postsListPresenter.attachView(this)
        postsListAdapter.setPostsListListener(this)
        postsListPresenter.getPostFromDB()

        fab.setOnClickListener(this)
        ic_reload.setOnClickListener(this)
        deleteIcon = ContextCompat.getDrawable(this, R.drawable.ic_delete)!!
        nav_view.setOnNavigationItemSelectedListener OnNavigationItemSelectedListener@{ item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    //message.setText(R.string.title_home)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_dashboard -> {
                    //message.setText(R.string.title_dashboard)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {
                postsListAdapter.removeItem(viewHolder as PostsListViewHolder)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                val iconMargin = (itemView.height - deleteIcon.intrinsicHeight) / 2
                if (dX > 0) {
                    swipeBackground.setBounds(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
                    deleteIcon.setBounds(
                        itemView.left + iconMargin, itemView.top + iconMargin,
                        itemView.left + iconMargin + deleteIcon.intrinsicWidth, itemView.bottom - iconMargin
                    )
                } else {
                    swipeBackground.setBounds(
                        itemView.right + dX.toInt(),
                        itemView.top,
                        itemView.right,
                        itemView.bottom
                    )
                    deleteIcon.setBounds(
                        itemView.right - iconMargin - deleteIcon.intrinsicWidth, itemView.top + iconMargin,
                        itemView.right - iconMargin, itemView.bottom - iconMargin
                    )
                }
                swipeBackground.draw(c)
                c.save()
                /**Avoid to show delete icon over the item*/
                if (dX > 0)
                    c.clipRect(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
                else
                    c.clipRect(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
                deleteIcon.draw(c)
                c.restore()
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(rv_posts_list)
    }

    private fun setupRecyclerView() {
        linearLayoutManager = LinearLayoutManager(this)
        rv_posts_list.apply {
            setHasFixedSize(true)
            adapter = postsListAdapter
            layoutManager = linearLayoutManager
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun injectDependency() {
        getActivityComponent().inject(this)
    }

    override fun showSelectedPostDetails(adapterPos: Int) {
        Toast.makeText(this, "POSICION $adapterPos", Toast.LENGTH_SHORT).show()
    }

    override fun successPostsRequest(itemList: List<Post>) {
        if(mutableList.size > 0)
            mutableList.clear()
        mutableList.addAll(itemList)
        postsListAdapter.populatePostsList(mutableList)
    }

    override fun showErrorView(errorMessage: String?) {
        showErrorMessage(root_layout, errorMessage)
    }

    override fun showNoNetworkExceptionAlert(errorMessage: String?) {
        showErrorMessage(root_layout, errorMessage)
    }

    override fun showLoading(show: Boolean) {
        if (show) {
            progressbar.visibility = View.VISIBLE
        } else {
            progressbar.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        postsListPresenter.detachView()
    }

    override fun deleteAllDBContentSuccess(itemList: List<Post>) {
        mutableList.clear()
        mutableList.addAll(itemList)
        postsListAdapter.populatePostsList(mutableList)
    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.fab -> postsListPresenter.deleteAllPostFromDB()
            R.id.ic_reload -> postsListPresenter.getPostFromDB()

        }
    }

}
