package com.brainstoriming.androidconcurency

import android.os.Looper
import android.util.Log

class DownloadThread(private var songName: List<String>) : Thread() {

    lateinit var mHandler: DownloadHandler

    override fun run() {
        super.run()

            Looper.prepare()
            mHandler = DownloadHandler()
            Looper.loop()

    }



    companion object {
        private const val TAG = "DownloadThread"
    }
}