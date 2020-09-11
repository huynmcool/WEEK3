package com.john117.string_travel.api

import android.util.Log
import com.john117.string_travel.model.DataResult
import com.john117.string_travel.model.FeedData
import com.john117.string_travel.utils.AUTHORIZATION
import com.john117.string_travel.utils.CURRENT_PER_PAGE
import io.reactivex.Single


class FeedApiCaller {
    private val _apiRestFull: FeedApi by lazy {
        RetrofitClientInstance.getHelperRestFull()!!.create(FeedApi::class.java)
    }

    fun getFeedList(
        authorization: String,
        page: Int
    ): Single<FeedData> {
        Log.d("AUTHORIZATION", AUTHORIZATION + authorization)
        return RetrofitClientInstance.buildRequest(
            _apiRestFull.getFeedList(
                AUTHORIZATION + authorization,
                page,
                CURRENT_PER_PAGE
            )
        )
    }

    fun savePost(
        authorization: String,
        id: Int
    ): Single<DataResult>  {
        return RetrofitClientInstance.buildRequest(
            _apiRestFull.savePost(
                AUTHORIZATION + authorization,
                id
            ))
    }

    fun likePost(
        authorization: String,
        id: Int
    ): Single<DataResult>  {

        return RetrofitClientInstance.buildRequest(
            _apiRestFull.likePost(
                AUTHORIZATION + authorization,
                id
            ))
    }

}