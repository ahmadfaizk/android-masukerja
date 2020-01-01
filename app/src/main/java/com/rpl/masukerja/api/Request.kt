package com.rpl.masukerja.api

import com.rpl.masukerja.api.response.*
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

    @FormUrlEncoded
    @POST("job/search")
    @Headers("Accept: application/json")
    fun searchJob(@Header("Authorization") token: String,
                  @Field("title") title: String?,
                  @Field("salary") salary: Int?,
                  @Field("location") location: String?,
                  @Field("field") field: String?): Call<ListJobResponse>

    @FormUrlEncoded
    @POST("job/favorite")
    fun setFavorite(@Header("Authorization") token: String,
                    @Field("id_job") id: Int): Call<MessageResponse>

    @GET("job/favorite")
    fun getFavorite(@Header("Authorization") token: String): Call<ListJobResponse>

    @GET("test")
    fun getTest(@Header("Authorization") token: String): Call<TestResponse>

    @GET("article")
    fun getArticle(@Header("Authorization") token: String): Call<ArticleResponse>

    @GET("test/show")
    fun getTestHistory(@Header("Authorization") token: String): Call<ListTestResponse>

    @FormUrlEncoded
    @POST("test/store")
    fun storeTest(@Header("Authorization") token: String,
                  @Field("introvert") introvert: Int,
                  @Field("extrovert") extrovert: Int,
                  @Field("sensing") sensing: Int,
                  @Field("intuiting") intuiting: Int,
                  @Field("thingking") thingking: Int,
                  @Field("feeling") feeling: Int,
                  @Field("judging") judging: Int,
                  @Field("perceiving") perceiving: Int): Call<TestResultResponse>
}