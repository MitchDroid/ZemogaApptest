package co.com.mjbarrerab.zemogaapptest.network.manager



import co.com.mjbarrerab.zemogaapptest.models.Post
import co.com.mjbarrerab.zemogaapptest.models.Users
import co.com.mjbarrerab.zemogaapptest.network.remote.APIService
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
     * Get Users by Id
     */
    fun getUsersById(userId : String): Single<Users> {
        return APIService.doGetUsersById(userId)
    }
}