package com.rpl.masukerja.api

import com.rpl.masukerja.model.User
import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("user")
    fun getUser(): Call<User>
}