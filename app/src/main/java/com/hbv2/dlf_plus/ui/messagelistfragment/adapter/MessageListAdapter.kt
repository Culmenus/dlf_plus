package com.hbv2.dlf_plus.ui.messagelistfragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hbv2.dlf_plus.data.model.Message
import com.hbv2.dlf_plus.data.model.MessageDTO
import com.hbv2.dlf_plus.data.model.User
import com.hbv2.dlf_plus.databinding.FragmentTopicMsgMeBinding
import com.hbv2.dlf_plus.databinding.FragmentTopicMsgOtherBinding
import com.hbv2.dlf_plus.ui.messagelistfragment.viewholder.ReceivedMessageHolder
import com.hbv2.dlf_plus.ui.messagelistfragment.viewholder.SentMessageHolder

class MessageListAdapter(
    private val messages: List<MessageDTO>,
    private val userID: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_MESSAGE_SENT: Int = 1;
        const val VIEW_TYPE_MESSAGE_RECEIVED: Int = 2;
    }

    override fun getItemViewType(position: Int): Int {
        val msg = messages[position]
        //breyta í current user í staðinn fyrir 1
        if (msg.userID == userID) {
            return VIEW_TYPE_MESSAGE_SENT
        }
        return VIEW_TYPE_MESSAGE_RECEIVED
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val from = LayoutInflater.from(parent.context)

        if(viewType == VIEW_TYPE_MESSAGE_SENT) {
            val binding = FragmentTopicMsgMeBinding.inflate(from, parent, false)
            return SentMessageHolder(binding)
        }

        val binding = FragmentTopicMsgOtherBinding.inflate(from, parent, false)
        return ReceivedMessageHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val msg = messages[position]
        //kotlin switch ?? veit ekki hversu hratt þó
        when (holder.itemViewType) {
            1 -> (holder as SentMessageHolder).bind(msg)
            2 -> (holder as ReceivedMessageHolder).bind(msg)
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }
}

