package com.brainstoriming.androidconcurency.conncurency

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper.myLooper
import android.os.Message
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.brainstoriming.androidconcurency.ui.MainActivity.Companion.MESSAGE_DATA
import com.brainstoriming.androidconcurency.service.MyDownloadService

class DownloadHandler : Handler(myLooper()!!) {

    private lateinit var mContext: Context
    private lateinit var service: MyDownloadService

    override fun handleMessage(msg: Message) {
        downloadSong(msg.obj.toString())
        service.stopSelfResult(msg.arg1)

        sendMessageToUI(msg.obj.toString())
    }

    private fun downloadSong(song: String) {
        Log.d(TAG, "runCode: Starting Download")
        Thread.sleep(4000)
        Log.d(TAG, "runCode: ${Thread.currentThread().name}")
        Log.d(TAG, "runCode: $song Downloaded...")

    }

    private fun sendMessageToUI(songName:String){
        Intent(SONG_KEY).also {
            it.putExtra(MESSAGE_DATA,songName)
            LocalBroadcastManager.getInstance(mContext).sendBroadcast(it)
        }
    }

    companion object {
        private const val TAG = "MyTags"
        const val SONG_KEY = "song_key"
    }

    fun setContext(context: Context){
        this.mContext = context
    }

    fun setDownloadService(service: MyDownloadService){
        this.service = service
    }
}