package co.com.mjbarrerab.zemogaapptest.network.remote


import co.com.mjbarrerab.zemogaapptest.BuildConfig
import co.com.mjbarrerab.zemogaapptest.models.Post
import co.com.mjbarrerab.zemogaapptest.models.Users
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * Created by miller.barrera on 08/08/2020.
 */

interface APIService {

    /**Get Posts ApiResponse
     * */
    @GET(BuildConfig.POST)
    fun doGetPost(): Single<List<Post>>

    /**Get Users BY iD ApiResponse
     * */
    @GET(BuildConfig.USER_BY_ID)
    fun doGetUsersById(@Path("id") userId : String): Single<Users>
}