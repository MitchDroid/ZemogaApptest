package co.com.mjbarrerab.zemogaapptest.ui.favorites

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import co.com.mjbarrerab.zemogaapptest.R
import co.com.mjbarrerab.zemogaapptest.data.models.Post
import co.com.mjbarrerab.zemogaapptest.ui.base.activity.BaseMVPActivity
import co.com.mjbarrerab.zemogaapptest.ui.posts.PostsListActivity
import co.com.mjbarrerab.zemogaapptest.ui.posts.PostsListAdapter
import co.com.mjbarrerab.zemogaapptest.ui.posts.PostsListListener
import co.com.mjbarrerab.zemogaapptest.ui.posts.PostsListPresenter
import co.com.mjbarrerab.zemogaapptest.ui.postsdetails.PostsDetailsActivity
import kotlinx.android.synthetic.main.activity_favorites_content.*
import kotlinx.android.synthetic.main.activity_posts.*
import kotlinx.android.synthetic.main.activity_posts_content.*
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by miller.barrera on 9/08/2020.
 */
class FavoritesActivity : BaseMVPActivity(), FavoritesContract.View, PostsListListener {

    @Inject
    lateinit var favoritesPresenter: FavoritesPresenter

    @Inject
    lateinit var postsListAdapter: PostsListAdapter

    private lateinit var linearLayoutManager: LinearLayoutManager
    private var mutableList: MutableList<Post> = mutableListOf()

    override fun getLayout(): Int {
        return R.layout.activity_favorites
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)
        injectDependency()
        setSupportActionBar(toolbar)
        setupRecyclerView()
        favoritesPresenter.attachView(this)
        postsListAdapter.setPostsListListener(this)
        favoritesPresenter.getFavoritesFromDB()

        nav_view_favorites.selectedItemId = R.id.navigation_favorites
        nav_view_favorites.setOnNavigationItemSelectedListener OnNavigationItemSelectedListener@{ item ->
            when (item.itemId) {
                R.id.navigation_post -> {
                    val intent = Intent(this, PostsListActivity::class.java)
                    intent.flags = (Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_favorites -> {
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    private fun setupRecyclerView() {
        linearLayoutManager = LinearLayoutManager(this)
        rv_favorites_list.apply {
            setHasFixedSize(true)
            adapter = postsListAdapter
            layoutManager = linearLayoutManager
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        }
    }

    override fun successFavoritesFromDB(itemList: List<Post>) {
        mutableList.addAll(itemList)
        postsListAdapter.populatePostsList(mutableList)
    }

    override fun showErrorView(errorMessage: String?) {
        TODO("Not yet implemented")
    }

    override fun showNoNetworkExceptionAlert(errorMessage: String?) {
        TODO("Not yet implemented")
    }

    override fun deleteAllDBContentSuccess(itemList: List<Post>) {
        TODO("Not yet implemented")
    }

    override fun showLoading(show: Boolean) = if (show) {
        fav_progressbar.visibility = View.VISIBLE
    } else {
        fav_progressbar.visibility = View.GONE
    }

    private fun injectDependency() {
        getActivityComponent().inject(this)
    }

    override fun showSelectedPostDetails(adapterPos: Int) {
        Timber.d("SELECTED ITEM $adapterPos")
    }

}