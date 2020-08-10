package co.com.mjbarrerab.zemogaapptest.data.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import co.com.mjbarrerab.zemogaapptest.data.models.Comments
import co.com.mjbarrerab.zemogaapptest.data.models.Post
import co.com.mjbarrerab.zemogaapptest.data.models.Users

/**
 * Created by miller.barrera on 8/08/2020.
 */
@Database(entities = [Post::class, Users::class], version = 1)
abstract class PostsDatabase : RoomDatabase() {

    abstract fun postDao(): PostsDao

    companion object{
        @Volatile
        private var INSTANCE: PostsDatabase? = null

        fun getDatabase(context: Context): PostsDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PostsDatabase::class.java,
                    "db_post.db"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
