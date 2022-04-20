package com.hbv2.dlf_plus.ui.messagelistfragment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hbv2.dlf_plus.data.model.Message
import com.hbv2.dlf_plus.networks.BackendApiClient

class MessageListViewModel : ViewModel() {
    private val messages = ArrayList<Message>()
    private val messagesLiveData = MutableLiveData<List<Message>>()

    private val backendApiClient = BackendApiClient()

    fun getTopicsLiveData(): MutableLiveData<List<Message>> = messagesLiveData

    fun addMessage( message: Message ) {
        messages.add(message)
        messagesLiveData.value = messages
    }
}