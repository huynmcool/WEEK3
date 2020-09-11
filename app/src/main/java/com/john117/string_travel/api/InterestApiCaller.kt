package com.john117.string_travel.api

import android.util.Log
import com.john117.string_travel.model.InterestData
import com.john117.string_travel.model.UserData
import com.john117.string_travel.utils.AUTHORIZATION
import io.reactivex.Single


class InterestApiCaller {
    private val _apiRestFull: InterestAPI by lazy {
        RetrofitClientInstance.getHelperRestFull()!!.create(InterestAPI::class.java)
    }

    fun getListInterest(authorization: String): Single<InterestData> {
        Log.d("AUTHORIZATION",AUTHORIZATION + authorization)
        return RetrofitClientInstance.buildRequest(
            _apiRestFull.getListInterest(
                AUTHORIZATION + authorization
            )
        )
    }

    fun putInterest(authorization: String, listsInterest: ArrayList<Int>): Single<UserData> {
        return RetrofitClientInstance.buildRequest(
            _apiRestFull.putInterest(
                authorization = AUTHORIZATION + authorization,
                listsInterest = listsInterest
            )
        )
    }
}