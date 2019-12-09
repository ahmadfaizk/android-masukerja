package com.rpl.masukerja.api

import com.rpl.masukerja.api.response.LoginResponse
import com.rpl.masukerja.api.response.MessageResponse
import com.rpl.masukerja.api.response.UserResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface Request{
    @FormUrlEncoded
    @POST("auth/login")
    fun login(@Field("email") email: String,
              @Field("password") password: String): Call<LoginResponse>

    @FormUrlEncoded
    @POST("auth/register")
    fun register(@Field("name") name: String,
                 @Field("email") email: String,
                 @Field("password") password: String): Call<ResponseBody>

    @GET("auth/user")
    fun getUser(@Header("Authorization") token: String): Call<UserResponse>

    @FormUrlEncoded
    @POST("auth/forgot-password")
    fun forgotPassword(@Field("email") email: String): Call<MessageResponse>

    @FormUrlEncoded
    @POST("auth/verify-otp")
    fun verifyOTP(@Field("email") email: String,
                  @Field("otp") otp: Int): Call<MessageResponse>

    @FormUrlEncoded
    @POST("auth/change-password")
    fun changePassword(@Field("email") email: String,
                       @Field("password") password: String,
                       @Field("otp") otp: Int): Call<MessageResponse>
}