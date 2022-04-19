package com.hbv2.dlf_plus.networks.websocket

import android.util.Log
import com.google.gson.Gson
import com.hbv2.dlf_plus.data.model.Message
import com.hbv2.dlf_plus.data.model.MessageDTO
import com.hbv2.dlf_plus.networks.misc.Constants
import io.reactivex.Completable
import io.reactivex.CompletableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import java.io.IOException
import java.util.concurrent.Callable
import java.util.function.Function

class WSChatClient {
    private lateinit var mStompClient: StompClient
    private var compositeDisposable = CompositeDisposable()
    var messagees = ArrayList<MessageDTO>()
    init {
        if(!::mStompClient.isInitialized){
            mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, Constants.WS_URL)
            mStompClient.connect()
        }
    }
    fun closeConnection(){
        if(::mStompClient.isInitialized && mStompClient.isConnected){
            mStompClient.disconnect()
        }
        compositeDisposable.dispose();
    }
    fun subscribe(threadId: Int, addItem: (msg: MessageDTO) -> Boolean){
        val subscription = mStompClient.topic("/thread/$threadId/get")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                addItem(Gson().fromJson(it.payload,MessageDTO::class.java))
            }
        compositeDisposable.add(subscription)
    }
    fun sendMessage(threadId: Int, message: MessageDTO){
        try {
            compositeDisposable.add(
                mStompClient.send("/app/thread/$threadId/send",
                    Gson().toJson(message))
                    .compose(applySchedulers())
                    .subscribe()
            )

        }catch(e: Throwable){
            Log.d("mStopClient", e.message!!);
            throw IOException("stompClient error")
        }
    }
    private fun applySchedulers(): CompletableTransformer? {
        return CompletableTransformer { upstream: Completable ->
            upstream
                .unsubscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }
    private fun  resetSubscriptions(){
        compositeDisposable.dispose()
        compositeDisposable = CompositeDisposable()
    }

}