package com.john117.string_travel.api

import android.util.Log
import com.john117.string_travel.model.UserData
import com.john117.string_travel.utils.AUTHORIZATION
import io.reactivex.Single

class UserApiCaller {
    private val _apiRestFull:UserApi by lazy {
        RetrofitClientInstance.getHelperRestFull()!!.create(UserApi::class.java)
    }

    fun login(username:String,password:String,fcmToken:String):Single<UserData>{
        return RetrofitClientInstance.buildRequest(
            _apiRestFull.signIn(
                username, password, fcmToken
            )
        )
    }

    fun forgotPassword(email: String): Single<UserData> {
        return RetrofitClientInstance.buildRequest(
            _apiRestFull.forgotPassword(email)
        )
    }

    fun resendEmailRegister(code: String): Single<UserData> {
        return RetrofitClientInstance.buildRequest(
            _apiRestFull.resendEmailRegister(code)
        )
    }

    fun changeProfile(
        file: String,
        username: String,
        bio: String,
        website: String,
        name: String,
        dayOfBirth: String,
        accessToken: String
    ): Single<UserData> {
        Log.d(
            "userchange",
            "$file|$username|$bio|$website|$name|$dayOfBirth|$AUTHORIZATION$accessToken"
        )
        return RetrofitClientInstance.buildRequest(
            _apiRestFull.changeProfile(
                file,
                username,
                bio,
                website,
                name,
                dayOfBirth,
                AUTHORIZATION + accessToken
            )
        )
    }

    fun getUserProfile(authorization: String, usersID: Int): Single<UserData> {
        return RetrofitClientInstance.buildRequest(
            _apiRestFull.getUserProfile(AUTHORIZATION + authorization, usersID)
        )
    }

    fun logOut(accessToken: String): Single<UserData> {
        return RetrofitClientInstance.buildRequest(
            _apiRestFull.logOut(AUTHORIZATION + accessToken)
        )
    }

    fun registerAccByEmail(
        username: String,
        name: String,
        dayOfBirth: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Single<UserData> {
        Log.d(
            "aaaa2", username + "|" +
                    name + "|" + dayOfBirth + "|" + email + "|" +
                    password + "|" + confirmPassword
        )
        return RetrofitClientInstance.buildRequest(
            _apiRestFull.registerAccByEmail(
                username = username,
                name = name,
                dayOfBirth = dayOfBirth,
                email = email,
                password = password,
                confirmPassword = confirmPassword
            )
        )
    }

}