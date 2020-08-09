package co.com.mjbarrerab.zemogaapptest.data.repository.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.com.mjbarrerab.zemogaapptest.data.models.Post
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

    @Query("SELECT * from tb_posts where post_user_id = :id LIMIT 1")
    fun loadPostsByUserId(id: Int): Single<Post>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPosts(posts: List<Post>): Completable

    @Query("DELETE FROM tb_posts")
    fun deleteAllPosts(): Completable
}