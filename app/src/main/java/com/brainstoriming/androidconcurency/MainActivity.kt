package com.brainstoriming.androidconcurency

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.brainstoriming.androidconcurency.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    fun clearText(view: View) {
        binding.tvLogs.text = ""
    }

    fun runCode(view: View) {
        Log.d(TAG, "runCode: ${Thread.currentThread().name}")
        Log.d(TAG, "runCode: Code is Running")

        showProgressBar(true)

        val runnable = Runnable {
            showProgressBar(false)
        }

        val handlerThread = Handler(Looper.getMainLooper())
        handlerThread.postDelayed(runnable,4000)


        binding.tvLogs.append("Running Code \n")
    }

    fun showProgressBar(shouldShow: Boolean) {
        binding.pbContent.isVisible = shouldShow
    }


    companion object{
        private const val TAG = "MainActivity"
    }
}