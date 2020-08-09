package co.com.mjbarrerab.zemogaapptest.models

import com.squareup.moshi.Json

data class Post(
    @field:Json(name ="body")
    var body: String?,
    @field:Json(name ="id")
    var id: Int?,
    @field:Json(name ="title")
    var title: String?,
    @field:Json(name ="userId")
    var userId: Int?
)