package com.brainstoriming.androidconcurency.conncurency

import android.os.Handler
import android.os.Looper.getMainLooper
import android.os.Message
import android.util.Log
import com.brainstoriming.androidconcurency.MainActivity

class DownloadHandler : Handler(getMainLooper()) {

    override fun handleMessage(msg: Message) {
        downloadSong(msg.obj.toString())
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
}