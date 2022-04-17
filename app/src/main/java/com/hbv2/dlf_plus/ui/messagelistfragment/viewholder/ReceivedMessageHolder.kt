package com.hbv2.dlf_plus.ui.messagelistfragment.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.hbv2.dlf_plus.data.model.Message
import com.hbv2.dlf_plus.databinding.FragmentTopicMsgOtherBinding
import java.text.SimpleDateFormat
import java.util.*


class ReceivedMessageHolder(
    private val msgOtherBinding: FragmentTopicMsgOtherBinding
) : RecyclerView.ViewHolder(msgOtherBinding.root) {
    private lateinit var msg: Message

    private fun Date.dateToString(format: String): String {
        val dateFormatter = SimpleDateFormat(format, Locale.getDefault())
        return dateFormatter.format(this)
    }

    fun bind(message: Message) {
        this.msg = message
        msgOtherBinding.textTopicMsgOther.text = message.message
        msgOtherBinding.textTopicDateOther.text = message.sentBy.username
        msgOtherBinding.textTopicTimestampOther.text = message.createdAt.dateToString("MMM. dd\nHH:mm")
    }
}