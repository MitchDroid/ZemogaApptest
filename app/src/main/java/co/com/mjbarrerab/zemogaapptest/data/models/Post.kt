package co.com.mjbarrerab.zemogaapptest.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "tb_posts")
data class Post(
    @ColumnInfo(name = "post_user_id")
    @field:Json(name ="userId")
    var userId: Int?,

    @ColumnInfo(name = "post_body")
    @field:Json(name ="body")
    var body: String?,

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "post_id")
    @field:Json(name ="id")
    var id: Int?,

    @ColumnInfo(name = "post_title")
    @field:Json(name ="title")
    var title: String?
)