package co.com.mjbarrerab.zemogaapptest.network.remote.factory


import co.com.mjbarrerab.zemogaapptest.BuildConfig
import co.com.mjbarrerab.zemogaapptest.network.remote.APIService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by miller.barrera on 08/08/2020.
 */
class Factory {
    companion object {
        private val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        private val client : OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()

        fun create(): APIService {
            val retrofit = Retrofit.Builder().baseUrl(BuildConfig.HOST)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client).build()

            return retrofit.create(APIService::class.java)
        }
    }


}