package co.com.mjbarrerab.zemogaapptest.models

import com.squareup.moshi.Json

data class Users(
    @field:Json(name = "address") var address: Address? = null,
    @field:Json(name ="company") var company: Company? = null,
    @field:Json(name ="email") var email: String? = null,
    @field:Json(name ="id") var id: Int? = null,
    @field:Json(name ="name") var name: String? = null,
    @field:Json(name ="phone") var phone: String? = null,
    @field:Json(name ="username") var username: String? = null,
    @field:Json(name ="website") var website: String? = null
)