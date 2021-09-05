package com.brainstoriming.androidconcurency

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.brainstoriming.androidconcurency.conncurency.DownloadThread
import com.brainstoriming.androidconcurency.databinding.ActivityMainBinding
import com.brainstoriming.androidconcurency.service.MyDownloadService
import com.brainstoriming.androidconcurency.service.MyDownloadService.Companion.KEY

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

//    private lateinit var mDownloadThread: DownloadThread
//
//    private lateinit var mHandler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

//        mHandler = object : Handler(Looper.getMainLooper()) {
//            override fun handleMessage(msg: Message) {
//                val messageString = msg.data.getString(MESSAGE_DATA)
//
//                Log.d(TAG, "handleMessage: $messageString")
////                showProgressBar(false)
//            }
//        }
//
//        mDownloadThread = DownloadThread(this)
//        mDownloadThread.name = "Download Thread"
//        mDownloadThread.start()
    }

    fun clearText(view: View) {
//        binding.tvLogs.text = ""

        Intent(this@MainActivity, MyDownloadService::class.java).apply {
            stopService(this)
        }
    }

    fun runCode(view: View) {
        Log.d(TAG, "runCode: Code is Running")

        Intent(this@MainActivity,MyDownloadService::class.java).apply {
            putExtra(KEY,"First Song")
            startService(this)
        }

//        showProgressBar(true)
//        for (item in Playlist.songsList) {
//            val message = Message.obtain()
//            message.obj = item
//            mDownloadThread.mHandler.sendMessage(message)
//        }
    }

    fun showProgressBar(shouldShow: Boolean) {
        binding.pbContent.isVisible = shouldShow
    }


    companion object {
        private const val TAG = "MyTags"
        const val MESSAGE_DATA = "message_data"
    }
}