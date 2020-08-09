package co.com.mjbarrerab.zemogaapptest.di.modules

import android.app.Activity
import android.content.Context
import co.com.mjbarrerab.zemogaapptest.data.repository.DataSource
import co.com.mjbarrerab.zemogaapptest.di.qualifier.ActivityContext
import co.com.mjbarrerab.zemogaapptest.ui.posts.PostsListAdapter
import co.com.mjbarrerab.zemogaapptest.ui.posts.PostsListContract
import co.com.mjbarrerab.zemogaapptest.ui.posts.PostsListPresenter
import co.com.mjbarrerab.zemogaapptest.utils.BaseSchedulerProvider
import co.com.mjbarrerab.zemogaapptest.utils.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

/**
 * Created by miller.barrera on 14/10/2019.
 */
@Module
class ActivityModule(activity: Activity) {

    var mActivity: Activity = activity

    @Provides
    internal fun provideActivity(): Activity {
        return mActivity
    }

    @Provides
    @ActivityContext
    internal fun provideContext(): Context {
        return mActivity
    }

    //Inject adapters and presenters
    @Provides
    fun providePostsListPresenter(): PostsListContract.Presenter {
        return PostsListPresenter(schedulerProvider = SchedulerProvider(),
            subscriptions = CompositeDisposable()
        )
    }


    @Singleton
    @Provides
    fun provideBaseSchedulerProvider(): BaseSchedulerProvider {
        return SchedulerProvider()
    }

    @Singleton
    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Provides
    internal fun providePostListAdapter(): PostsListAdapter = PostsListAdapter()

    @Provides
    @Singleton
    fun provideDataSource(): DataSource {
        return DataSource(provideContext())
    }

}