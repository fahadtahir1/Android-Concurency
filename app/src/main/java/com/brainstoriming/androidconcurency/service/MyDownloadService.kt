package com.brainstoriming.androidconcurency.service

import android.app.Service
import android.content.Intent
import android.util.Log

class MyDownloadService : Service() {

    private lateinit var bindedString: String

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: Download Service Created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand: Service Started")
        bindedString = intent?.extras?.getString(KEY).toString()
        Log.d(TAG, "onStartCommand: $bindedString")

        val thread = Thread(Runnable {
            downloadSong(bindedString)
        })

        thread.start()

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): Nothing? = null

    private fun downloadSong(song: String) {
        Log.d(TAG, "runCode: Starting Download")
        Thread.sleep(4000)
        Log.d(TAG, "runCode: ${Thread.currentThread().name}")
        Log.d(TAG, "runCode: $song Downloaded...")
    }

    companion object {
        private const val TAG = "MyTags"
        const val KEY = "key_string"
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: Download Service Destroyed")
    }
}