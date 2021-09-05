package com.brainstoriming.androidconcurency.conncurency

import android.os.Looper
import com.brainstoriming.androidconcurency.MainActivity

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