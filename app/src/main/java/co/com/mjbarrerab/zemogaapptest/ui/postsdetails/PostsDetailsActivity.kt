package co.com.mjbarrerab.zemogaapptest.ui.postsdetails

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import co.com.mjbarrerab.zemogaapptest.R
import co.com.mjbarrerab.zemogaapptest.data.models.Comments
import co.com.mjbarrerab.zemogaapptest.data.models.Post
import co.com.mjbarrerab.zemogaapptest.data.models.Users
import co.com.mjbarrerab.zemogaapptest.ui.base.activity.BaseMVPActivity
import co.com.mjbarrerab.zemogaapptest.ui.posts.PostsListAdapter
import kotlinx.android.synthetic.main.activity_posts.*
import kotlinx.android.synthetic.main.activity_posts.root_layout
import kotlinx.android.synthetic.main.activity_posts_content.*
import kotlinx.android.synthetic.main.activity_posts_details.*
import kotlinx.android.synthetic.main.activity_posts_details_content.*
import javax.inject.Inject

class PostsDetailsActivity : BaseMVPActivity(), PostsDetailsContract.View, View.OnClickListener {

    @Inject
    lateinit var postsDetailsPresenter: PostsDetailsPresenter
    @Inject
    lateinit var commentsAdapter: CommentsAdapter
    private var mutableList: MutableList<Comments> = mutableListOf()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var postId: Int = 1
    private var userId: Int = 1

    override fun getLayout(): Int {
        return R.layout.activity_posts_details
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)
        injectDependency()
        setupToolbar()
        setupRecyclerView()
        postId = intent.getIntExtra("SELECTED_POST_ID", 1)
        userId = intent.getIntExtra("SELECTED_USER_ID", 1)
        postsDetailsPresenter.attachView(this)
        postsDetailsPresenter.getPostDetailsByIdFromDB(postId)
        postsDetailsPresenter.getUserByIdFromDB(userId)
        postsDetailsPresenter.getComments(postId)

        ic_unselected_star.setOnClickListener(this)

    }


    private fun injectDependency() {
        getActivityComponent().inject(this)
    }


    override fun showErrorView(errorMessage: String?) {
        showErrorMessage(root_layout, errorMessage)
    }

    override fun showNoNetworkExceptionAlert(errorMessage: String?) {
        showErrorMessage(root_layout, errorMessage)
    }

    override fun showLoading(show: Boolean) = if (show) {
        details_progressbar.visibility = View.VISIBLE
    } else {
        details_progressbar.visibility = View.GONE
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ic_unselected_star -> postsDetailsPresenter.saveAsFavorite(postId, true)
        }
    }

    override fun successPostsByIdRequest(post: Post) {
        if (post.isFavorite)
            ic_unselected_star.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_star_fav))
        tv_desc_content.text = post.body
    }

    override fun successFavoriteAdded() {
        ic_unselected_star.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_star_fav))
    }
    override fun successCommentsRequest(list: List<Comments>) {
        mutableList.addAll(list)
        commentsAdapter.populatePostsList(mutableList)
    }

    override fun successUserByIdRequest(user: Users) {
        tv_user_name.text = user.name
        tv_user_email.text = user.email
        tv_user_phone.text = user.phone
        tv_user_website.text = user.website
    }

    private fun setupRecyclerView() {
        linearLayoutManager = LinearLayoutManager(this)
        rv_comments_list.apply {
            setHasFixedSize(true)
            adapter = commentsAdapter
            layoutManager = linearLayoutManager
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        postsDetailsPresenter.detachView()
    }
}
