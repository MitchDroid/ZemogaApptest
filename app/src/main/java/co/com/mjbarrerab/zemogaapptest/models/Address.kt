package co.com.mjbarrerab.zemogaapptest.models

import com.squareup.moshi.Json

data class Address(
    @field:Json(name ="city")
    var city: String?,
    @field:Json(name ="geo")
    var geo: Geo?,
    @field:Json(name ="street")
    var street: String?,
    @field:Json(name ="suite")
    var suite: String?,
    @field:Json(name ="zipcode")
    var zipcode: String?
)