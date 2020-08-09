package co.com.mjbarrerab.zemogaapptest.data.network.remote.factory


import co.com.mjbarrerab.zemogaapptest.BuildConfig
import co.com.mjbarrerab.zemogaapptest.data.network.remote.APIService
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by miller.barrera on 08/08/2020.
 */
class Factory {
    companion object {
        private val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }

        private val client : OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .apply {
            this.addInterceptor(interceptor)
        }.build()

        @Synchronized
        fun create(): APIService {
            val retrofit = Retrofit.Builder().baseUrl(BuildConfig.HOST)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(client).build()

            return retrofit.create(APIService::class.java)
        }
    }


}