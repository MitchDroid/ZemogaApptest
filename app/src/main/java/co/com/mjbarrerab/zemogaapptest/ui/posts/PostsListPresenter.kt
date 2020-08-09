package co.com.mjbarrerab.zemogaapptest.ui.posts

import android.content.Context
import co.com.mjbarrerab.zemogaapptest.ktextensions.mapNetworkErrors
import co.com.mjbarrerab.zemogaapptest.models.Post
import co.com.mjbarrerab.zemogaapptest.network.manager.DataManager
import co.com.mjbarrerab.zemogaapptest.network.remote.exceptions.NetworkExceptions
import co.com.mjbarrerab.zemogaapptest.ui.base.BasePresenter
import co.com.mjbarrerab.zemogaapptest.utils.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
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

    override fun requestGetPosts() {
        checkViewAttached()
        getMvpView()?.showLoading(true)
        val postsSubscription = dataManager.getPosts()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .unsubscribeOn(schedulerProvider.io())
            .mapNetworkErrors()
            .subscribeBy(onError = {
                displayErrorHandling(it)
            }, onSuccess = {
                getMvpView()?.successPostsRequest(it)
                getMvpView()?.showLoading(false)
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
            is NetworkExceptions.NoNetworkException -> getMvpView()?.showNoNetworkExceptionAlert()
            is NetworkExceptions.ConnectException -> getMvpView()?.showNoNetworkExceptionAlert()
            is NetworkExceptions.ServerUnreachableException -> getMvpView()?.showErrorView()
            is NetworkExceptions.HttpCallFailureException -> getMvpView()?.showErrorView()
            else -> getMvpView()?.showErrorView()
        }
        getMvpView()?.showLoading(false)
    }

}