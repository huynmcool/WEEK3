package com.john117.string_travel.ui.feed.adapter

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.john117.string_travel.R
import com.john117.string_travel.model.Feed
import com.john117.string_travel.ui.feed.events.TypeFeedEvent
import com.john117.string_travel.utils.BaseViewHolder
import com.john117.string_travel.utils.invisible
import com.john117.string_travel.utils.visible
import kotlinx.android.synthetic.main.item_feed_poi.view.*


class PoiViewHolder(
    itemView: View,
    private val events: (position: Int, type: TypeFeedEvent) -> Unit
) : BaseViewHolder<Feed>(itemView) {

    override fun bind(item: Feed, position: Int) {
        itemView.tv_title_poi.text = item.title
        itemView.tv_location_poi.text = item.address

        // Avatar
        Glide.with(itemView)
            .load(item.user?.profilePhoto)
            .error(R.drawable.ic_acc_img)
            .into(itemView.iv_avatar_poi)
        itemView.txt_username_poi.text = item.user?.username

        // Todo --> change image into list image slider
        Glide.with(itemView)
            .load(item.photos?.get(0)?.url?.medium)
            .error(R.drawable.no_image)
            .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
            .into(itemView.img_poi)

        // Comment
        if (item.commentCounter != 0) {
            itemView.tv_cmt_count_poi.visible()
            itemView.tv_cmt_count_poi.text = item.commentCounter.toString()
        } else
            itemView.tv_cmt_count_poi.invisible()
        if (item.strungCounter != 0)
            itemView.btn_string_poi.text = item.strungCounter.toString()

        // Like
        if (item.likeCounter != 0) {
            itemView.tv_like_count_poi.visible()
            itemView.tv_like_count_poi.text = item.likeCounter.toString()
        } else
            itemView.tv_like_count_poi.invisible()
        itemView.btn_like_poi.isSelected = item.isLiked!!

        // Button like click listener
        itemView.btn_like_poi.setOnClickListener {
            itemView.btn_like_poi.isSelected = !itemView.btn_like_poi.isSelected
            if (itemView.btn_like_poi.isSelected) {
                item.likeCounter = item.likeCounter?.plus(1)
            } else
                item.likeCounter = item.likeCounter?.minus(1)

            if (item.likeCounter != 0) {
                itemView.tv_like_count_poi.visible()
                itemView.tv_like_count_poi.text = item.likeCounter.toString()
            } else
                itemView.tv_like_count_poi.invisible()
            events.invoke(position, TypeFeedEvent.LIKE)
        }

        //  Button comment click
        itemView.btn_cmt_poi.setOnClickListener {
            events.invoke(position, TypeFeedEvent.COMMENT)
        }

        //  Button show more
        itemView.btn_show_more_poi.setOnClickListener {
            events.invoke(position, TypeFeedEvent.SHOW_MORE)
        }

        // Button String listener
        itemView.btn_string_poi.setOnClickListener {
            events.invoke(position, TypeFeedEvent.STRING_POI)
        }

    }
}