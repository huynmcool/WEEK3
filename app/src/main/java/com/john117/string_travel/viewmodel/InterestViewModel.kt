package com.john117.string_travel.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.john117.string_travel.api.InterestApiCaller
import com.john117.string_travel.model.InterestData
import com.john117.string_travel.model.UserData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class InterestViewModel : ViewModel() {
    private val compo by lazy { CompositeDisposable() }
    private val apiManager: InterestApiCaller by lazy { InterestApiCaller() }
    val dataPutInterest = MutableLiveData<UserData>().apply { value = null }
    val dataInterest = MutableLiveData<InterestData>().apply { value = null }

    fun getListInterest(accessToken: String) {
        compo.add(
            apiManager.getListInterest(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    dataInterest.value = it
                }, {})
        )
    }

    fun putListInterest(accessToken: String, listsInterest: ArrayList<Int>) {
        compo.add(
            apiManager.putInterest(accessToken, listsInterest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    dataPutInterest.value = it
                }, {})
        )
    }

}