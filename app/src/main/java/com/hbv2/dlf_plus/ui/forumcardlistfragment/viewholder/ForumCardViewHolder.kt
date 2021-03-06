package com.hbv2.dlf_plus.ui.forumcardlistfragment.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.hbv2.dlf_plus.data.model.Forum
import com.hbv2.dlf_plus.databinding.CardCellBinding
import com.hbv2.dlf_plus.ui.forumcardlistfragment.ForumClickListener

class ForumCardViewHolder(
    private val cardCellBinding: CardCellBinding,
    private val clickListener: ForumClickListener
) : RecyclerView.ViewHolder(cardCellBinding.root) {
    fun bindForum(forum: Forum) {
        cardCellBinding.cover.setImageResource(forum.cover)
        cardCellBinding.name.text = forum.name
        cardCellBinding.courseId.text = forum.courseId
        cardCellBinding.cardView.setOnClickListener {
            clickListener.onClick(forum)
        }
    }
}