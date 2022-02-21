package com.hbv2.dlf_plus

import androidx.recyclerview.widget.RecyclerView
import com.hbv2.dlf_plus.databinding.CardCellBinding

class CardViewHolder(
    private val cardCellBinding: CardCellBinding
) : RecyclerView.ViewHolder(cardCellBinding.root)
{
    fun bindForum(forum: Forum) {
        cardCellBinding.cover.setImageResource(forum.cover)
        cardCellBinding.name.text = forum.name
        cardCellBinding.courseId.text = forum.courseId
    }
}