package com.brainstoriming.androidconcurency.conncurency

import android.content.Context
import android.os.Handler
import android.os.Looper.getMainLooper
import android.os.Message
import android.util.Log
import com.brainstoriming.androidconcurency.MainActivity
import com.brainstoriming.androidconcurency.service.MyDownloadService

class DownloadHandler : Handler(getMainLooper()) {

    private lateinit var mContext: Context
    private lateinit var service: MyDownloadService

    override fun handleMessage(msg: Message) {
        downloadSong(msg.obj.toString())
        service.stopSelfResult(msg.arg1)
    }

    private fun downloadSong(song: String) {
        Log.d(TAG, "runCode: Starting Download")
        Thread.sleep(4000)
        Log.d(TAG, "runCode: ${Thread.currentThread().name}")
        Log.d(TAG, "runCode: $song Downloaded...")

    }

    companion object {
        private const val TAG = "MyTags"
    }

    fun setContext(context: Context){
        this.mContext = context
    }

    fun setDownloadService(service: MyDownloadService){
        this.service = service
    }
}