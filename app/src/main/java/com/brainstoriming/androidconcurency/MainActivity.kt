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

    private lateinit var mHandler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mHandler = object : Handler(Looper.getMainLooper()){
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)

            }
        }
    }

    fun clearText(view: View) {
        binding.tvLogs.text = ""
    }

    fun runCode(view: View) {
        Log.d(TAG, "runCode: ${Thread.currentThread().name}")
        Log.d(TAG, "runCode: Code is Running")

        showProgressBar(true)

        val runnable = Runnable {
//            showProgressBar(false)
            Thread.sleep(4000)
            Log.d(TAG, "runCode: ${Thread.currentThread().name}")
            Log.d(TAG, "runCode: Downloaded")
        }

        val thread = Thread(runnable)
        thread.name = "Download Thread"
        thread.start()

    }

    private fun showProgressBar(shouldShow: Boolean) {
        binding.pbContent.isVisible = shouldShow
    }


    companion object{
        private const val TAG = "MainActivity"
    }
}