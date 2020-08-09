package co.com.mjbarrerab.zemogaapptest.data.models

import com.squareup.moshi.Json

data class Company(
    @field:Json(name ="bs")
    var bs: String?,
    @field:Json(name ="catchPhrase")
    var catchPhrase: String?,
    @field:Json(name ="name")
    var name: String?
)