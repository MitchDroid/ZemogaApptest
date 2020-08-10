package co.com.mjbarrerab.zemogaapptest.data.models

import androidx.room.ColumnInfo
import com.squareup.moshi.Json

data class Company(
    @ColumnInfo(name = "user_bs")
    @field:Json(name ="bs")
    var bs: String?,

    @ColumnInfo(name = "user_catch")
    @field:Json(name ="catchPhrase")
    var catchPhrase: String?,

    @ColumnInfo(name = "user_company_name")
    @field:Json(name ="name")
    var name: String?
)