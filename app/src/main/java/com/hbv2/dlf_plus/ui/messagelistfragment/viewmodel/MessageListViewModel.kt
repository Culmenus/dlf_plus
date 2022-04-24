package com.hbv2.dlf_plus.ui.messagelistfragment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hbv2.dlf_plus.data.model.Message
import com.hbv2.dlf_plus.data.model.MessageDTO
import kotlin.collections.ArrayList

class MessageListViewModel : ViewModel() {
    private val messages = ArrayList<MessageDTO>()
    private val messagesLiveData = MutableLiveData<List<MessageDTO>>()

    fun getMessagesLiveData(): MutableLiveData<List<MessageDTO>> = messagesLiveData

    fun getMessages(): ArrayList<MessageDTO> = messages

    fun addMessage( message: Message ) {
        val msg = MessageDTO(message)
        messages.add(msg)
        messagesLiveData.value = messages
    }
    fun addMessage(message: MessageDTO) {
        messages.add(message)
        messagesLiveData.value = messages
    }
}