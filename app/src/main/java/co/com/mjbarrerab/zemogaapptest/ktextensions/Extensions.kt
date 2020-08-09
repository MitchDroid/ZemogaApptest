package co.com.mjbarrerab.zemogaapptest.ktextensions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import co.com.mjbarrerab.zemogaapptest.network.remote.exceptions.NetworkExceptions

import io.reactivex.Single
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by miller.barrera on 13/03/2019.
 */

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

inline fun <FRAGMENT : Fragment> FRAGMENT.putArgs(argsBuilder: Bundle.() -> Unit): FRAGMENT = this.apply { arguments = Bundle().apply(argsBuilder) }

fun <T> Single<T>.mapNetworkErrors(): Single<T> =
        this.onErrorResumeNext { error ->
            when (error) {
                is ConnectException -> Single.error(NetworkExceptions.NoNetworkException(error))
                is SocketTimeoutException -> Single.error(NetworkExceptions.NoNetworkException(error))
                is UnknownHostException -> Single.error(NetworkExceptions.ServerUnreachableException(error))
                is HttpException -> Single.error(NetworkExceptions.HttpCallFailureException(error))
                else -> Single.error(error)
            }
        }