package co.com.mjbarrerab.zemogaapptest.di.components



import co.com.mjbarrerab.zemogaapptest.ui.posts.PostsListActivity
import co.com.mjbarrerab.zemogaapptest.di.modules.ActivityModule
import co.com.mjbarrerab.zemogaapptest.di.modules.NetworkModule
import co.com.mjbarrerab.zemogaapptest.di.scope.ActivityScope
import dagger.Component
import javax.inject.Singleton

/**
 * Created by miller.barrera on 14/10/2019.
 */
@Singleton
@ActivityScope
@Component(modules = [ActivityModule::class, NetworkModule::class])
interface ActivityComponent {

    fun inject(postsListActivity: PostsListActivity)
    //fun inject(recipesDetailsActivity: RecipesDetailsActivity)

}