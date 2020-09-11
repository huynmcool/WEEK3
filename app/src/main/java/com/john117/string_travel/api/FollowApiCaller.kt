package com.john117.string_travel.api

import android.util.Log
import com.john117.string_travel.model.FollowData
import com.john117.string_travel.model.UserData
import com.john117.string_travel.utils.AUTHORIZATION
import io.reactivex.Single

class FollowApiCaller {
    private val _apiRestFull: FollowApi by lazy {
        RetrofitClientInstance.getHelperRestFull()!!.create(FollowApi::class.java)
    }

    fun getUserList(
        authorization: String,
        page: Int,
        currentPerPage: String
    ): Single<FollowData> {
        Log.d("AUTHORIZATION", AUTHORIZATION + authorization)
        return RetrofitClientInstance.buildRequest(
            _apiRestFull.getUserList(
                AUTHORIZATION + authorization,
                page,
                currentPerPage
            )
        )
    }

    fun postFollowUser(authorization: String, userID: Int): Single<UserData> {
        return RetrofitClientInstance.buildRequest(
            _apiRestFull.postFollowUser(
                AUTHORIZATION + authorization,
                userID
            )
        )
    }
}