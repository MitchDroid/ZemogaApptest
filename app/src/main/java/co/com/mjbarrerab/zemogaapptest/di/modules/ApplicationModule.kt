package co.com.mjbarrerab.zemogaapptest.di.modules

import android.app.Application
import android.content.Context
import co.com.mjbarrerab.zemogaapptest.application.ApplicationController
import co.com.mjbarrerab.zemogaapptest.di.qualifier.ApplicationContext
import co.com.mjbarrerab.zemogaapptest.di.scope.ActivityScope
import dagger.Module
import dagger.Provides


/**
 * Created by miller.barrera.
 */

@Module
class ApplicationModule(private val baseApp: ApplicationController){

    //All sub-components intended to use Dagger2 @Inject should be listed here.

    @Provides
    @ActivityScope
    fun provideApplication(): Application {
        return baseApp
    }

    @Provides
    @ApplicationContext
    internal fun provideApplicationContext(): Context {
        return baseApp
    }

}