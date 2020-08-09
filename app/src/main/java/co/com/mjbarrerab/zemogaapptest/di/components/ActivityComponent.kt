package co.com.mjbarrerab.zemogaapptest.di.components



import co.com.mjbarrerab.zemogaapptest.di.modules.ActivityModule
import co.com.mjbarrerab.zemogaapptest.di.modules.NetworkModule
import co.com.mjbarrerab.zemogaapptest.di.scope.ActivityScope
import co.com.mjbarrerab.zemogaapptest.ui.posts.PostsListActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by miller.barrera on 08/08/2020.
 */
@Singleton
@ActivityScope
@Component(modules = [ActivityModule::class, NetworkModule::class])
interface ActivityComponent {

    //Inject activities
    fun inject(postsListActivity: PostsListActivity)

}