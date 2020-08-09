package co.com.mjbarrerab.zemogaapptest.di.scope

import javax.inject.Scope

/**
 * Created by miller.barrera on 12/02/2019.
 */
/**
 * Scope for instances which live as long as the activity
 */
@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope