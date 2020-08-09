package co.com.mjbarrerab.zemogaapptest.application

import android.app.Application
import co.com.mjbarrerab.zemogaapptest.BuildConfig
import co.com.mjbarrerab.zemogaapptest.di.components.ApplicationComponent
import co.com.mjbarrerab.zemogaapptest.di.components.DaggerApplicationComponent
import co.com.mjbarrerab.zemogaapptest.di.modules.ApplicationModule

/**
 * Created by miller.barrera on 8/08/2020.
 */
@Suppress("DEPRECATION")
class ApplicationController: Application() {
    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        instance = this
        setup()

        if (BuildConfig.DEBUG) {
            // Maybe TimberPlant etc.
        }
    }

    private fun setup() {
        component = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this)).build()
        component.inject(this)
    }

    fun getApplicationComponent(): ApplicationComponent {
        return component
    }

    companion object {
        lateinit var instance: ApplicationController private set
    }
}