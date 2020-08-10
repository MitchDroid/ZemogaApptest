package co.com.mjbarrerab.zemogaapptest.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "tb_users")
data class Users(

    @Ignore
    @field:Json(name = "address") var address: Address? = null,

    @Ignore
    @field:Json(name ="company") var company: Company? = null,

    @ColumnInfo(name = "user_email")
    @field:Json(name ="email") var email: String? = null,

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "user_id")
    @field:Json(name ="id") var id: Int? = null,

    @ColumnInfo(name = "user_name")
    @field:Json(name ="name") var name: String? = null,

    @ColumnInfo(name = "user_phone")
    @field:Json(name ="phone") var phone: String? = null,

    @ColumnInfo(name = "user_username")
    @field:Json(name ="username") var username: String? = null,

    @ColumnInfo(name = "user_website")
    @field:Json(name ="website") var website: String? = null
)