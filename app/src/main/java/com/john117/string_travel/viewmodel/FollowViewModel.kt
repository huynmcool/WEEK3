package com.john117.string_travel.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.john117.string_travel.api.FollowApiCaller
import com.john117.string_travel.model.FollowData
import com.john117.string_travel.model.UserData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FollowViewModel : ViewModel() {
    private val compo by lazy { CompositeDisposable() }
    private val apiManager: FollowApiCaller by lazy { FollowApiCaller() }
    val listUserFollow = MutableLiveData<FollowData>().apply { value = null }
    val resultDataFollow = MutableLiveData<UserData>().apply { value = null }

    fun getUserFollowList(
        accessToken: String,
        page: Int,
        currentPerPage: String
    ) {
        compo.add(
            apiManager.getUserList(accessToken, page, currentPerPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    listUserFollow.value = it
                }, {})
        )
    }

    fun postFollowUser(
        accessToken: String,
        userID: Int
    ) {
        compo.add(
            apiManager.postFollowUser(accessToken, userID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    resultDataFollow.value = it
                }, {})
        )
    }

}