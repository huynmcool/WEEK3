package com.john117.string_travel.api

import com.john117.string_travel.model.CommentData
import com.john117.string_travel.model.DataResult
import com.john117.string_travel.utils.AUTHORIZATION
import com.john117.string_travel.utils.CURRENT_PER_PAGE
import io.reactivex.Single


class CommentApiCaller {
    private val _apiRestFull: CommentApi by lazy {
        RetrofitClientInstance.getHelperRestFull()!!.create(CommentApi::class.java)
    }

    fun getCommentList(
        authorization: String,
        page: Int,
        id: Int
    ): Single<CommentData> {
        return RetrofitClientInstance.buildRequest(
            _apiRestFull.getCommentList(
                id,
                AUTHORIZATION + authorization,
                page,
                CURRENT_PER_PAGE
            )
        )
    }

    fun addComment(
        id: Int?,
        comment: String?,
        replyID: Int?,
        commentChildID: Int?,
        tagUsername: ArrayList<String>?,
        accessToken: String?
    ): Single<DataResult> {
        return RetrofitClientInstance.buildRequest(
            _apiRestFull.addComment(
                id,
                comment,
                replyID,
                commentChildID,
                tagUsername,
                AUTHORIZATION + accessToken
            )
        )
    }

    fun deleteComment(
        cmtId: Int?,
        feedID: Int?,
        accessToken: String?
    ): Single<DataResult> {
        return RetrofitClientInstance.buildRequest(
            _apiRestFull.deleteComment(cmtId, feedID, AUTHORIZATION + accessToken)
        )
    }

}