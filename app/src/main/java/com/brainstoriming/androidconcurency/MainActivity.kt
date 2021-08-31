package com.brainstoriming.androidconcurency

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.brainstoriming.androidconcurency.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var mDownloadThread: DownloadThread

    private lateinit var mHandler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mHandler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                val messageString = msg.data.getString(MESSAGE_DATA)

                Log.d(TAG, "handleMessage: $messageString")
//                showProgressBar(false)
            }
        }

        mDownloadThread = DownloadThread(Playlist.songsList)
        mDownloadThread.name = "Download Thread"
        mDownloadThread.start()
    }

    fun clearText(view: View) {
        binding.tvLogs.text = ""
    }

    fun runCode(view: View) {
        Log.d(TAG, "runCode: Code is Running")

//        showProgressBar(true)
        for (item in Playlist.songsList) {
            val message = Message.obtain()
            message.obj = item
            mDownloadThread.mHandler.sendMessage(message)
        }
    }

    private fun showProgressBar(shouldShow: Boolean) {
        binding.pbContent.isVisible = shouldShow
    }


    companion object {
        private const val TAG = "MainActivity"
        const val MESSAGE_DATA = "message_data"
    }
}