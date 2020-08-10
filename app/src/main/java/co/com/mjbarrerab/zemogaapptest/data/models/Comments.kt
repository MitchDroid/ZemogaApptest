package co.com.mjbarrerab.zemogaapptest.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json


data class Comments(
    @field:Json(name = "body")
    val body: String?,

    @field:Json(name ="email")
    val email: String?,

    @field:Json(name ="id")
    val id: Int?,

    @field:Json(name ="name")
    val name: String?,

    @field:Json(name ="postId")
    val postId: Int?
)