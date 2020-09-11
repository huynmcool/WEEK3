package com.john117.string_travel.ui.comment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.john117.string_travel.R
import com.john117.string_travel.model.Comment
import com.john117.string_travel.utils.BaseViewHolder
import kotlinx.android.synthetic.main.item_comment.view.*



class CommentAdapter(
    private val onUserTagClick: (userId: Int) -> Unit,
    private val onCommentMoreClick: (comment: Comment) -> Unit
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var listComment: ArrayList<Comment>? = arrayListOf()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val view = LayoutInflater
            .from(viewGroup.context)
            .inflate(R.layout.activity_comment, viewGroup, false)
        return CommentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listComment?.size ?: 0
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = listComment?.get(position)
        when (holder) {
            is CommentViewHolder -> holder.bind(element as Comment, position)
            else -> throw IllegalArgumentException()
        }
    }

    fun addFeedData(arrFeed: ArrayList<Comment>?) {
        if (arrFeed != null) {
            listComment = arrFeed
            notifyDataSetChanged()
        }
    }

    fun updateFeedData(arrFeed: ArrayList<Comment>?) {
        if (arrFeed != null) {
            listComment?.addAll(arrFeed)
            notifyDataSetChanged()
        }
    }

    inner class CommentViewHolder(
        itemView: View
    ) : BaseViewHolder<Comment>(itemView) {
        override fun bind(item: Comment, position: Int) {
            // Avatar
            Glide.with(itemView)
                .load(item.user?.profilePhoto)
                .error(R.drawable.ic_acc_img)
                .into(itemView.iv_avatar_cmt)
            // User name
            itemView.txt_username_cmt.text = item.user?.username
            // Content comment
            itemView.tv_comment_content.text = item.description
            // Show more listener
            itemView.btn_show_more_cmt.setOnClickListener { onCommentMoreClick(item) }
        }
    }
}
