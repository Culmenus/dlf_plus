package com.hbv2.dlf_plus.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hbv2.dlf_plus.viewholders.CardViewHolder
import com.hbv2.dlf_plus.Forum
import com.hbv2.dlf_plus.interfaces.ForumClickListener
import com.hbv2.dlf_plus.databinding.CardCellBinding

class CardAdapter (
    private val forums: List<Forum>,
    private val clickListener: ForumClickListener
        )
        : RecyclerView.Adapter<CardViewHolder>()
{
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
                val from = LayoutInflater.from(parent.context)
                val binding = CardCellBinding.inflate(from, parent, false)
                return CardViewHolder(binding, clickListener)
        }

        override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
                holder.bindForum(forums[position])
        }

        override fun getItemCount(): Int {
                return forums.size
        }

}