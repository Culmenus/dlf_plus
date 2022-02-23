package com.hbv2.dlf_plus

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hbv2.dlf_plus.databinding.CardTopicBinding

class TopicAdapter(
    private val topics: List<Topic>,
    private val clickListener: TopicClickListener
    )
    : RecyclerView.Adapter<TopicViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = CardTopicBinding.inflate(from, parent, false)
        return TopicViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        holder.bindTopic(topics[position])
    }

    override fun getItemCount(): Int = topics.size

}