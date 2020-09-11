package com.john117.string_travel.api

import com.john117.string_travel.model.InterestData
import com.john117.string_travel.model.UserData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Query

interface InterestAPI {
    @GET("interest-categories-list")
    fun getListInterest(
        @Header("Authorization") authorization: String
    ): Call<InterestData>

    @PUT("users-interest-categories-select")
    fun putInterest(
        @Header("Authorization") authorization: String,
        @Query("lists_interest[]") listsInterest: ArrayList<Int>
    ): Call<UserData>

}