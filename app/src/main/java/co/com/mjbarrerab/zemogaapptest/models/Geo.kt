package co.com.mjbarrerab.zemogaapptest.models

import com.squareup.moshi.Json

data class Geo(
    @field:Json(name ="lat")
    var lat: String?,
    @field:Json(name ="lng")
    var lng: String?
)