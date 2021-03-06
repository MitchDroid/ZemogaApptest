package co.com.mjbarrerab.zemogaapptest.ui.posts

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.com.mjbarrerab.zemogaapptest.R
import co.com.mjbarrerab.zemogaapptest.data.models.Post
import co.com.mjbarrerab.zemogaapptest.ui.base.activity.BaseMVPActivity
import co.com.mjbarrerab.zemogaapptest.ui.favorites.FavoritesActivity
import kotlinx.android.synthetic.main.activity_posts.*
import kotlinx.android.synthetic.main.activity_posts_content.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class PostsListActivity : BaseMVPActivity(), PostsListContract.View, PostsListListener,  View.OnClickListener {

    @Inject
    lateinit var postsListPresenter: PostsListPresenter
    @Inject
    lateinit var postsListAdapter: PostsListAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var deleteIcon: Drawable
    private var mutableList: MutableList<Post> = mutableListOf()
    private var swipeBackground: ColorDrawable = ColorDrawable(Color.parseColor("#FF0000"))
    internal var textlength = 0
    var arraySort: MutableList<Post> = mutableListOf()

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
        postsListPresenter.getUsers()

        fab.setOnClickListener(this)
        ic_reload.setOnClickListener(this)
        deleteIcon = ContextCompat.getDrawable(this, R.drawable.ic_delete)!!

        mSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textlength = mSearch!!.text.length
                arraySort.clear()
                for (i in mutableList.indices) {
                    if (textlength <= mutableList[i].title!!.length) {
                        if (mutableList[i].title!!.toLowerCase(Locale.ROOT).trim().contains(
                                mSearch!!.text.toString().toLowerCase(Locale.ROOT).trim { it <= ' ' })
                        ) {
                            arraySort.add(mutableList[i])
                        }
                    }
                }
                postsListAdapter.populatePostsList(arraySort)
                setupRecyclerView()
            }

        })


        nav_view.selectedItemId = R.id.navigation_post
        nav_view.setOnNavigationItemSelectedListener OnNavigationItemSelectedListener@{ item ->
            when (item.itemId) {
                R.id.navigation_post -> {
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_favorites -> {
                    val intent = Intent(this, FavoritesActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    overridePendingTransition(0,0)
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
                val post = postsListAdapter.getDeletedItem(viewHolder as PostsListViewHolder)
                postsListPresenter.deletePostById(post.id)
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
        val post = postsListAdapter.getSelectedItem(adapterPos)
        postsListPresenter.updateIsNewPost(post.id, false)
        postsListPresenter.goToItemaDetailsActivity(post, this)
        postsListAdapter.notifyDataSetChanged()
    }

    override fun successPostsRequest(itemList: List<Post>) {
        if(mutableList.size > 0)
            mutableList.clear()
        arraySort.addAll(itemList)
        mutableList.addAll(itemList)
        postsListAdapter.populatePostsList(mutableList)
    }

    override fun showErrorView(errorMessage: String?) {
        showErrorMessage(root_layout, errorMessage)
    }

    override fun showNoNetworkExceptionAlert(errorMessage: String?) {
        showErrorMessage(root_layout, errorMessage)
    }

    override fun showLoading(show: Boolean) = if (show) {
        progressbar.visibility = View.VISIBLE
    } else {
         progressbar.visibility = View.GONE
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
