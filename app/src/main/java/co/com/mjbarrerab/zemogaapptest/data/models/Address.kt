package co.com.mjbarrerab.zemogaapptest.data.models

import androidx.room.ColumnInfo
import com.squareup.moshi.Json

data class Address(
    @ColumnInfo(name = "user_city")
    @field:Json(name ="city")
    var city: String?,

    @ColumnInfo(name = "user_geo")
    @field:Json(name ="geo")
    var geo: Geo?,

    @ColumnInfo(name = "user_street")
    @field:Json(name ="street")
    var street: String?,

    @ColumnInfo(name = "user_suite")
    @field:Json(name ="suite")
    var suite: String?,

    @ColumnInfo(name = "user_zipcode")
    @field:Json(name ="zipcode")
    var zipcode: String?
)