package com.john117.string_travel.model


data class FollowData (
    val code: Int?,
    var data: ArrayList<UserInfo>?,
    val message: String?,
    val metadata: Metadata?,
    val status: Boolean?
)