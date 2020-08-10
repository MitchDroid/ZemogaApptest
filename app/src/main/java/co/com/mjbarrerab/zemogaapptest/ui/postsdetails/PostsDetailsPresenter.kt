package co.com.mjbarrerab.zemogaapptest.ui.postsdetails

import android.content.Context
import co.com.mjbarrerab.zemogaapptest.data.models.Post
import co.com.mjbarrerab.zemogaapptest.data.network.manager.DataManager
import co.com.mjbarrerab.zemogaapptest.data.network.remote.exceptions.NetworkExceptions
import co.com.mjbarrerab.zemogaapptest.data.repository.DataSource
import co.com.mjbarrerab.zemogaapptest.ktextensions.mapNetworkErrors
import co.com.mjbarrerab.zemogaapptest.ui.base.BasePresenter
import co.com.mjbarrerab.zemogaapptest.utils.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by miller.barrera on 8/08/2020.
 */
class PostsDetailsPresenter @Inject constructor(
    private val schedulerProvider: BaseSchedulerProvider,
    private var subscriptions: CompositeDisposable
) : BasePresenter<PostsDetailsContract.View>(), PostsDetailsContract.Presenter {

    @Inject
    lateinit var dataManager: DataManager

    @Inject
    lateinit var postsDetailsNavigator: PostsDetailsNavigator

    @Inject
    lateinit var dataSource: DataSource

    override fun attachView(mvpView: PostsDetailsContract.View) {
        super.attachView(mvpView)
        if (!subscriptions.isDisposed) {
            subscriptions = CompositeDisposable()
        }
    }

    override fun detachView() {
        super.detachView()
        subscriptions.clear()
    }

    override fun getPostDetailsByIdFromDB(postId: Int) {
        checkViewAttached()
        val dbSubscription = dataSource.database.postDao().loadPostsById(postId)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .unsubscribeOn(schedulerProvider.io())
            .mapNetworkErrors()
            .subscribeBy(onError = {
                Timber.e(it)
            }, onSuccess = {
                if (it != null) {
                    Timber.d("======= GET POST BY ID FROM BD")
                    getMvpView()?.successPostsByIdRequest(it)
                }
            })
        subscriptions.add(dbSubscription)
    }


    override fun getUserByIdFromDB(userId: Int) {
        checkViewAttached()
        getMvpView()?.showLoading(true)
        val userSubscription = dataSource.database.postDao().loadUserById(userId)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .unsubscribeOn(schedulerProvider.io())
            .mapNetworkErrors()
            .subscribeBy(onError = {
                Timber.e(it)
                displayErrorHandling(it)
            }, onSuccess = {
                if (it != null) {
                    Timber.d("======= GET USER BY ID FROM BD")
                    getMvpView()?.successUserByIdRequest(it)
                } else {
                    getMvpView()?.showLoading(false)
                }
            })
        subscriptions.add(userSubscription)
    }

    override fun getComments(postId: Int) {
        checkViewAttached()
        getMvpView()?.showLoading(true)
        val commentsSubscription = dataManager.getCommentsByPostId(postId)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .unsubscribeOn(schedulerProvider.io())
            .mapNetworkErrors()
            .subscribeBy(onError = {
                displayErrorHandling(it)
            }, onSuccess = {
                getMvpView()?.successCommentsRequest(it)
                getMvpView()?.showLoading(false)
            })
        subscriptions.add(commentsSubscription)
    }

    override fun saveAsFavorite(postId: Int, isFavorite: Boolean) {
        checkViewAttached()
        val favoriteSubscription = dataSource.database.postDao().updateFavoritePost(isFavorite, postId)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .unsubscribeOn(schedulerProvider.io())
            .subscribeBy(onComplete = {
                Timber.d("======= FAVORITE ITEM UPDATED SUCCESS")
                getMvpView()?.successFavoriteAdded()
            },onError = {
                getMvpView()?.showErrorView(it.toString())
            })
        subscriptions.add(favoriteSubscription)
    }

    /**
     * with exception classes mapped,
     * UI code for handling different scenarios is much cleaner and easy to maintain:
     * @param Throwable error
     * @return Void
     */
    override fun displayErrorHandling(error: Throwable) {
        when (error) {
            is NetworkExceptions.NoNetworkException -> getMvpView()?.showNoNetworkExceptionAlert(
                error.message
            )
            is NetworkExceptions.ConnectException -> getMvpView()?.showNoNetworkExceptionAlert(error.message)
            is NetworkExceptions.ServerUnreachableException -> getMvpView()?.showNoNetworkExceptionAlert(
                error.message
            )
            is NetworkExceptions.HttpCallFailureException -> getMvpView()?.showNoNetworkExceptionAlert(
                error.message
            )
            else -> getMvpView()?.showErrorView(error.message)
        }
        getMvpView()?.showLoading(false)
    }

}