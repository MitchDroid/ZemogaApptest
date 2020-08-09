package co.com.mjbarrerab.zemogaapptest.ui.posts

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
class PostsListPresenter @Inject constructor(
    private val schedulerProvider: BaseSchedulerProvider,
    private var subscriptions: CompositeDisposable
) : BasePresenter<PostsListContract.View>(), PostsListContract.Presenter {

    @Inject
    lateinit var dataManager: DataManager
    @Inject
    lateinit var postsListNavigator: PostsListNavigator
    @Inject
    lateinit var dataSource: DataSource

    override fun attachView(mvpView: PostsListContract.View) {
        super.attachView(mvpView)
        if (!subscriptions.isDisposed) {
            subscriptions = CompositeDisposable()
        }
    }

    override fun detachView() {
        super.detachView()
        subscriptions.clear()
    }

    override fun getFromDB() {
        val dbSubscription = dataSource.database.postDao().loadPosts()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .unsubscribeOn(schedulerProvider.io())
            .mapNetworkErrors()
            .subscribeBy(onError = {
                Timber.e(it)
            }, onSuccess = {
                if (it.isNotEmpty()) {
                    getMvpView()?.successPostsRequest(it)
                    getMvpView()?.showLoading(false)
                } else {
                    requestGetPosts()
                }
            })
        subscriptions.add(dbSubscription)
    }


    override fun requestGetPosts() {
        getMvpView()?.showLoading(true)
        val postsSubscription = dataManager.getPosts()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .unsubscribeOn(schedulerProvider.io())
            .mapNetworkErrors()
            .subscribeBy(onError = {
                displayErrorHandling(it)
            }, onSuccess = {
                val postsDao = dataSource.database.postDao()
                postsDao.deleteAllPosts()
                    .subscribeBy(onComplete = {
                        Timber.d("Posts  delete succeed")
                        postsDao.insertPosts(it)
                            .subscribeBy(onComplete = {
                                Timber.d("Posts  new records store succeed")
                                postsDao.loadPosts()
                                    .observeOn(schedulerProvider.ui())
                                    .subscribeBy(onSuccess = {
                                        getMvpView()?.successPostsRequest(it)
                                        getMvpView()?.showLoading(false)
                                    }, onError = {
                                        displayErrorHandling(it)
                                    })
                            }, onError = {
                                displayErrorHandling(it)
                            })
                    }, onError = {
                        displayErrorHandling(it)
                    })
            })
        subscriptions.add(postsSubscription)
    }

    override fun goToItemaDetailsActivity(selectedItem: Post, context: Context) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * with exception classes mapped,
     * UI code for handling different scenarios is much cleaner and easy to maintain:
     * @param Throwable error
     * @return Void
     */
    override fun displayErrorHandling(error: Throwable) {
        when (error) {
            is NetworkExceptions.NoNetworkException -> getMvpView()?.showNoNetworkExceptionAlert(error.message)
            is NetworkExceptions.ConnectException -> getMvpView()?.showNoNetworkExceptionAlert(error.message)
            is NetworkExceptions.ServerUnreachableException -> getMvpView()?.showNoNetworkExceptionAlert(error.message)
            is NetworkExceptions.HttpCallFailureException -> getMvpView()?.showNoNetworkExceptionAlert(error.message)
            else -> getMvpView()?.showErrorView(error.message)
        }
        getMvpView()?.showLoading(false)
    }

}