package com.john117.string_travel.ui.feed.events

data class FeedEvent(
    val isLike: Boolean?,
    val likeCounter: Int?,
    val commentCounter: Int?
)