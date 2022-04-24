package com.hbv2.dlf_plus.ui.topiclistfragment.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.hbv2.dlf_plus.data.model.Topic
import com.hbv2.dlf_plus.databinding.ListItemTopicBinding
import com.hbv2.dlf_plus.ui.topiclistfragment.TopicClickListener

class TopicViewHolder(
    private val listItemTopicBinding: ListItemTopicBinding,
    private val clickListener: TopicClickListener
) : RecyclerView.ViewHolder(listItemTopicBinding.root) {
    private lateinit var topic: Topic

    fun bindTopic(topic: Topic) {
        this.topic = topic
        listItemTopicBinding.topicTitle.text = topic.title
        listItemTopicBinding.topicDescription.text = topic.description

        listItemTopicBinding.root.setOnClickListener {
            clickListener.onClick(topic)
        }

    }

}

