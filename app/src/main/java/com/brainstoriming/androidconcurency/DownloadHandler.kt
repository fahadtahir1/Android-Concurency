package com.brainstoriming.androidconcurency

import android.os.Handler
import android.os.Looper
import android.os.Looper.getMainLooper
import android.os.Looper.myLooper
import android.os.Message
import android.util.Log

class DownloadHandler(private val activity: MainActivity) : Handler(getMainLooper()) {

    override fun handleMessage(msg: Message) {
        downloadSong(msg.obj.toString())
    }

    private fun downloadSong(song: String) {
        Log.d(TAG, "runCode: Starting Download")
        Thread.sleep(4000)
        Log.d(TAG, "runCode: ${Thread.currentThread().name}")
        Log.d(TAG, "runCode: $song Downloaded...")

        activity.runOnUiThread {
            Log.d(TAG, "runCode: ${Thread.currentThread().name}")
            Log.d(TAG, "runCode: $song Downloaded...")
            activity.showProgressBar(false)
        }
    }

    companion object {
        private const val TAG = "MyTags"
    }
}