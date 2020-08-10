package co.com.mjbarrerab.zemogaapptest.ui.favorites

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
class FavoritesPresenter @Inject constructor(
    private val schedulerProvider: BaseSchedulerProvider,
    private var subscriptions: CompositeDisposable
) : BasePresenter<FavoritesContract.View>(), FavoritesContract.Presenter {

    @Inject
    lateinit var dataManager: DataManager
    @Inject
    lateinit var dataSource: DataSource

    override fun attachView(mvpView: FavoritesContract.View) {
        super.attachView(mvpView)
        if (!subscriptions.isDisposed) {
            subscriptions = CompositeDisposable()
        }
    }

    override fun detachView() {
        super.detachView()
        subscriptions.clear()
    }

    override fun getFavoritesFromDB() {
        checkViewAttached()
        getMvpView()?.showLoading(true)
        val dbSubscription = dataSource.database.postDao().loadFavoritePosts()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .unsubscribeOn(schedulerProvider.io())
            .mapNetworkErrors()
            .subscribeBy(onError = {
                Timber.e(it)
            }, onSuccess = {
                if (it.isNotEmpty()) {
                    Timber.d("======= TRAE DATOS DE BD")
                    for (post in it)
                        post.isNewPost = false

                    getMvpView()?.successFavoritesFromDB(it)
                    getMvpView()?.showLoading(false)
                } else {
                    Timber.d("======= ERROR NO TRAE DATOS DE FAVORITOS BD")
                    getMvpView()?.showLoading(false)
                }
            })
        subscriptions.add(dbSubscription)
    }


    override fun deleteAllPostFromDB() {
        val dbSubscription = dataSource.database.postDao().loadPosts()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .unsubscribeOn(schedulerProvider.io())
            .mapNetworkErrors()
            .subscribeBy(onError = {
                Timber.e(it)
            }, onSuccess = {
                if (it.isNotEmpty()) {
                    val postsDao = dataSource.database.postDao()
                    postsDao.deleteAllPosts()
                        .subscribeBy(onComplete = {
                            Timber.d("All Posts  deleted succeed")
                            getMvpView()?.deleteAllDBContentSuccess(mutableListOf())
                        }, onError = {error ->
                            Timber.e(error)
                        })
                } else {
                    getMvpView()?.showErrorView("No hay datos que borrar")
                }
            })
        subscriptions.add(dbSubscription)
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