package co.com.mjbarrerab.zemogaapptest.data.network.manager



import co.com.mjbarrerab.zemogaapptest.data.models.Comments
import co.com.mjbarrerab.zemogaapptest.data.models.Post
import co.com.mjbarrerab.zemogaapptest.data.models.Users
import co.com.mjbarrerab.zemogaapptest.data.network.remote.APIService
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by miller.barrera on 08/08/2020.
 */
@Singleton
class DataManager @Inject constructor(private var APIService: APIService) {

    /******************************************************************
     * Backend
     ******************************************************************/

    /**
     * Get Post
     */
    fun getPosts(): Single<List<Post>> {
        return APIService.doGetPost()
    }

    /**
     * Get Users
     */
    fun getUsers(): Single<List<Users>> {
        return APIService.doGetUsers()
    }

    /**
     * Get Users by Id
     */
    fun getUsersById(userId : Int): Single<Users> {
        return APIService.doGetUsersById(userId)
    }

    /**
     * Get Comments by Post Id
     */
    fun getCommentsByPostId(postId : Int): Single<List<Comments>>  {
        return APIService.doGetCommentsByPostId(postId)
    }
}