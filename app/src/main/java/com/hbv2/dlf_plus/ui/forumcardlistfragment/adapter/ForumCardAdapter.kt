package com.hbv2.dlf_plus.ui.forumcardlistfragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hbv2.dlf_plus.ui.forumcardlistfragment.viewholder.ForumCardViewHolder
import com.hbv2.dlf_plus.data.model.Forum
import com.hbv2.dlf_plus.ui.forumcardlistfragment.ForumClickListener
import com.hbv2.dlf_plus.databinding.CardCellBinding

class ForumCardAdapter(
    private val forums: List<Forum>,
    private val clickListener: ForumClickListener
) : RecyclerView.Adapter<ForumCardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForumCardViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = CardCellBinding.inflate(from, parent, false)
        return ForumCardViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: ForumCardViewHolder, position: Int) {
        holder.bindForum(forums[position])
    }

    override fun getItemCount(): Int {
        return forums.size
    }

}