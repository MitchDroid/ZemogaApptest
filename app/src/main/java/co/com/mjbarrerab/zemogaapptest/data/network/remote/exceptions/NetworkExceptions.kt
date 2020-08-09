package co.com.mjbarrerab.zemogaapptest.data.network.remote.exceptions
/**
 * Created by miller.barrera on 08/08/2020.
 */
open class NetworkExceptions(error: Throwable) : RuntimeException(error) {
    class NoNetworkException(error: Throwable): NetworkExceptions(error)
    class ConnectException(error: Throwable) : NetworkExceptions(error)
    class ServerUnreachableException(error: Throwable) : NetworkExceptions(error)
    class HttpCallFailureException(error: Throwable) : NetworkExceptions(error)
}