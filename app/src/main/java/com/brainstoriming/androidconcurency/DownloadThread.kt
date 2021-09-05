package com.brainstoriming.androidconcurency

import android.os.Looper

class DownloadThread(private val activity: MainActivity) : Thread() {

    lateinit var mHandler: DownloadHandler

    override fun run() {
        super.run()

        Looper.prepare()
        mHandler = DownloadHandler(activity)
        Looper.loop()

    }


    companion object {
        private const val TAG = "MyTags"
    }
}