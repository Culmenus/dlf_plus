package com.hbv2.dlf_plus.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.hbv2.dlf_plus.Topic
import com.hbv2.dlf_plus.interfaces.TopicClickListener
import com.hbv2.dlf_plus.databinding.CardTopicBinding

class TopicViewHolder (
    private val cardTopicBinding: CardTopicBinding,
    private val clickListener: TopicClickListener
) : RecyclerView.ViewHolder(cardTopicBinding.root)
{
    fun bindTopic(topic: Topic) {
        cardTopicBinding.name.text = topic.title
        cardTopicBinding.description.text = topic.description

        cardTopicBinding.topicCardView.setOnClickListener {
            clickListener.onClick(topic)
        }
    }
}

