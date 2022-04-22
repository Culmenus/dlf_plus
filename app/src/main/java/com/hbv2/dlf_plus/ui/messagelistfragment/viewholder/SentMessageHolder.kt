package com.hbv2.dlf_plus.ui.messagelistfragment.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.hbv2.dlf_plus.data.model.Message
import com.hbv2.dlf_plus.data.model.MessageDTO
import com.hbv2.dlf_plus.databinding.FragmentTopicMsgMeBinding
import java.text.SimpleDateFormat
import java.util.*

class SentMessageHolder(
    private val msgMeBinding: FragmentTopicMsgMeBinding
) : RecyclerView.ViewHolder(msgMeBinding.root) {
    private lateinit var msg: MessageDTO

    private fun Date.dateToString(format: String): String {
        val dateFormatter = SimpleDateFormat(format, Locale.getDefault())
        return dateFormatter.format(this)
    }

    fun bind(message: MessageDTO) {
        this.msg = message
        msgMeBinding.textTopicMsgMe.text = message.message
        msgMeBinding.textTopicTimestampMe.text = message.createdAt?.dateToString("MMM. dd\nHH:mm")
    }
}