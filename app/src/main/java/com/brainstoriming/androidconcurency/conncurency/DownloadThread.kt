package com.brainstoriming.androidconcurency.conncurency

import android.os.Looper

class DownloadThread : Thread() {

    lateinit var mHandler: DownloadHandler

    override fun run() {
        super.run()

        Looper.prepare()
        mHandler = DownloadHandler()
        Looper.loop()

    }


    companion object {
        private const val TAG = "MyTags"
    }
}