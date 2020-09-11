package com.john117.string_travel.utils

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId

fun getFcmToken(): String {
    var token = ""
    FirebaseInstanceId.getInstance().instanceId
        .addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            } else {
                Log.d("Firebasetoken", task.result?.token.toString())
                token = task.result?.token.toString()
            }
        })
    return token
}