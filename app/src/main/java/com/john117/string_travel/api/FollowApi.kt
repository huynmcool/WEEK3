package com.john117.string_travel.api

import com.john117.string_travel.model.FollowData
import com.john117.string_travel.model.UserData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface FollowApi {

    @GET("users-list")
    fun getUserList(
        @Header("Authorization") authentication: String,
        @Query("page") page: Int,
        @Query("current_per_page") currentPerPage: String
    ): Call<FollowData>

    @POST("follow-users")
    fun postFollowUser(
        @Header("Authorization") authorization: String,
        @Query("users_id") userID: Int
    ): Call<UserData>

}