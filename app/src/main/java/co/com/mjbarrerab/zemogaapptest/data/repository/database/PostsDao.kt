package co.com.mjbarrerab.zemogaapptest.data.repository.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.com.mjbarrerab.zemogaapptest.data.models.Post
import co.com.mjbarrerab.zemogaapptest.data.models.Users
import io.reactivex.Completable
import io.reactivex.Single


/**
 * Created by miller.barrera on 8/08/2020.
 */
/**
 * Data access class Posts Event Model.
 * Insert, update, select, delete operation/query using RxJava so thread
 * scheduling, non block ui operation can be maintained easily.
 */
@Dao
interface PostsDao {
    @Query("SELECT * FROM tb_posts")
    fun loadPosts(): Single<List<Post>>

    @Query("UPDATE tb_posts SET is_favorite = :isFavorite WHERE post_id = :id")
    fun updateFavoritePost(isFavorite: Boolean, id: Int): Completable

    @Query("UPDATE tb_posts SET is_new_post = :isNewPost WHERE post_id = :id")
    fun updateNewPost(isNewPost: Boolean, id: Int?): Completable

    @Query("SELECT * FROM tb_posts where is_favorite = 1")
    fun loadFavoritePosts(): Single<List<Post>>

    @Query("SELECT * FROM tb_users")
    fun loadUsers(): Single<List<Users>>

    @Query("SELECT * from tb_posts where post_id = :id LIMIT 1")
    fun loadPostsById(id: Int): Single<Post>

    @Query("SELECT * from tb_users where user_id = :id LIMIT 1")
    fun loadUserById(id: Int): Single<Users>

    @Query("SELECT * from tb_posts where post_user_id = :id LIMIT 1")
    fun loadPostsByUserId(id: Int): Single<Post>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPosts(posts: List<Post>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(posts: List<Users>): Completable

    @Query("DELETE FROM tb_posts  where post_id = :id")
    fun deletePostById(id: Int?): Completable

    @Query("DELETE FROM tb_posts")
    fun deleteAllPosts(): Completable

    @Query("DELETE FROM tb_users")
    fun deleteAllUsers(): Completable

}