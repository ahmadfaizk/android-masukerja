package com.rpl.masukerja.api.response

data class LoginResponse(
    var message: String? = null,
    var code: Int? = null,
    var token: String? = null
)