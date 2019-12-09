package com.rpl.masukerja.api.response

import com.google.gson.annotations.SerializedName
import com.rpl.masukerja.model.User

data class UserResponse(
    @SerializedName("user")
    var user: User? = null
)