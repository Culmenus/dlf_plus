package com.hbv2.dlf_plus.ui.topiclistfragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hbv2.dlf_plus.data.model.Topic
import com.hbv2.dlf_plus.databinding.ListItemTopicBinding
import com.hbv2.dlf_plus.ui.topiclistfragment.TopicClickListener
import com.hbv2.dlf_plus.ui.topiclistfragment.viewholder.TopicViewHolder

class TopicAdapter(
    private val topics: List<Topic>,
    private val clickListener: TopicClickListener
    )
    : RecyclerView.Adapter<TopicViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = ListItemTopicBinding.inflate(from, parent, false)
        return TopicViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        val topic = topics[position]
        holder.bindTopic(topic)
    }

    override fun getItemCount(): Int = topics.size

}