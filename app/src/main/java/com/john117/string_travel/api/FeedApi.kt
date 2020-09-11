package com.john117.string_travel.api

import com.john117.string_travel.model.DataResult
import com.john117.string_travel.model.FeedData
import retrofit2.Call
import retrofit2.http.*


interface FeedApi {
    @GET("feed")
    fun getFeedList(
        @Header("Authorization") authentication: String,
        @Query("page") page: Int,
        @Query("current_per_page") currentPerPage: Int
    ): Call<FeedData>

    @FormUrlEncoded
    @POST("post-save")
    fun savePost(
        @Header("Authorization") authentication: String,
        @Field("id") id: Int
    ): Call<DataResult>

    @FormUrlEncoded
    @PUT("like")
    fun likePost(
        @Header("Authorization") authentication: String,
        @Field("ipps_id") id: Int
    ): Call<DataResult>
}