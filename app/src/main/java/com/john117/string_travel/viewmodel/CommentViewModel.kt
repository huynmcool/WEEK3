package com.john117.string_travel.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.john117.string_travel.api.CommentApiCaller
import com.john117.string_travel.model.CommentData
import com.john117.string_travel.model.DataResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class CommentViewModel : ViewModel() {
    private val compo by lazy { CompositeDisposable() }
    private val apiManager: CommentApiCaller by lazy { CommentApiCaller() }
    val commentData = MutableLiveData<CommentData>().apply { value = null }
    val cmtResult = MutableLiveData<DataResult>().apply { value = null }
    var page = MutableLiveData<Int>().apply { value = 1 }
    var errorData = MutableLiveData<Boolean>().apply { value = false }

    fun getCommentList(
        accessToken: String,
        _page: Int = 1,
        id: Int
    ) {
        compo.add(
            apiManager.getCommentList(accessToken, _page, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    errorData.value = false
                    commentData.value = it
                }, {
                    errorData.value = true
                })
        )
        page.value = page.value?.plus(1)
    }

    fun addComment(
        id: Int?,
        comment: String?,
        replyID: Int?,
        commentChildID: Int?,
        tagUsername: ArrayList<String>?,
        accessToken: String?
    ) {
        compo.add(
            apiManager.addComment(id, comment, replyID, commentChildID, tagUsername, accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    errorData.value = false
                    cmtResult.value = it
                }, {
                    errorData.value = true
                })
        )
        page.value = page.value?.plus(1)
    }

    fun deleteComment(
        cmtId: Int?,
        feedID: Int?,
        accessToken: String?
    ) {
        compo.add(
            apiManager.deleteComment(cmtId, feedID, accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    errorData.value = false
                    cmtResult.value = it
                }, {
                    errorData.value = true
                })
        )
    }
}