package co.com.mjbarrerab.zemogaapptest.di.components

import co.com.mjbarrerab.zemogaapptest.application.ApplicationController
import co.com.mjbarrerab.zemogaapptest.di.modules.ApplicationModule
import co.com.mjbarrerab.zemogaapptest.di.scope.ActivityScope
import dagger.Component
import javax.inject.Singleton

/**
 * Created by miller.barrera.
 */

@Singleton
@ActivityScope
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    //Inject Application
    fun inject(application: ApplicationController)


}