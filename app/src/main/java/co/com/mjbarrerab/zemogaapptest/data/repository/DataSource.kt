package co.com.mjbarrerab.zemogaapptest.data.repository

import android.content.Context
import co.com.mjbarrerab.zemogaapptest.data.repository.database.PostsDatabase
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by miller.barrera on 8/08/2020.
 */
/**
 * DataSource class to access database and api.
 * This object is created  through dagger (DI) and it is used
 * in dependency injection through Object Graph.
 * The scope is Singleton it instance life will be entire application
 */
@Singleton
class DataSource @Inject constructor(context: Context) {
    val database = PostsDatabase.getDatabase(context)
    //val api = RestApi.api
}