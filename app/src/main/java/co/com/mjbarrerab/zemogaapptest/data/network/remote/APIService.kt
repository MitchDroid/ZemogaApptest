package co.com.mjbarrerab.zemogaapptest.data.network.remote


import co.com.mjbarrerab.zemogaapptest.BuildConfig
import co.com.mjbarrerab.zemogaapptest.data.models.Comments
import co.com.mjbarrerab.zemogaapptest.data.models.Post
import co.com.mjbarrerab.zemogaapptest.data.models.Users
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

    /**Get Post BY iD ApiResponse
     * */
    @GET(BuildConfig.POST_BY_ID)
    fun doGetPostsById(@Path("id") userId : String): Single<Post>

    /**Get Users  ApiResponse
     * */
    @GET(BuildConfig.USERS)
    fun doGetUsers(): Single<List<Users>>

    /**Get Users  ApiResponse
     * */
    @GET(BuildConfig.COMMENTS_BY_POST_ID)
    fun doGetCommentsByPostId(@Path("id") postId: Int): Single<List<Comments>>

    /**Get Users BY iD ApiResponse
     * */
    @GET(BuildConfig.USER_BY_ID)
    fun doGetUsersById(@Path("id") userId : Int): Single<Users>
}