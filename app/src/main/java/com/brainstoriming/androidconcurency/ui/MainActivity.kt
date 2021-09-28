package com.brainstoriming.androidconcurency.ui

import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.brainstoriming.androidconcurency.R
import com.brainstoriming.androidconcurency.databinding.ActivityMainBinding
import com.brainstoriming.androidconcurency.service.MusicPlayerService
import com.brainstoriming.androidconcurency.service.MusicPlayerService.Companion.MESSAGE_KEY
import com.brainstoriming.androidconcurency.service.MusicPlayerService.Companion.MUSIC_COMPLETE
import com.brainstoriming.androidconcurency.service.MusicPlayerService.Companion.MUSIC_SERVICE_ACTION_START
import com.brainstoriming.androidconcurency.service.MyDownloadService
import com.brainstoriming.androidconcurency.service.MyForegroundService

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var service: MusicPlayerService? = null

    private var isBound = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, ibinder: IBinder?) {
            val binder = ibinder as MusicPlayerService.MusicPlayerBinder
            service = binder.service
            isBound = true

            Log.d(TAG, "onServiceConnected: $isBound")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            service = null
            isBound = false

            Log.d(TAG, "onServiceDisconnected: $isBound")
        }
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val myString = intent?.extras?.getString(MESSAGE_KEY)
            if (myString == "done") {
                binding.btPlay.text = "Play"
            }


//                intent?.extras?.getString(MESSAGE_DATA) != null -> {
//
//                    val songName = intent.extras?.getString(MESSAGE_DATA)
//
//                    Log.d(TAG, "onReceive: $songName")
//                    Log.d(TAG, "onReceive: ${Thread.currentThread().name}")
//                }


        }
    }

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

        Intent(this@MainActivity, MyForegroundService::class.java).apply {
            startService(this)
        }

//        for (item in Playlist.songsList) {
//            Intent(this@MainActivity, MyDownloadService::class.java).apply {
//                putExtra(KEY, item)
//                startService(this)
//            }
//        }

//        showProgressBar(true)
//        for (item in Playlist.songsList) {
//            val message = Message.obtain()
//            message.obj = item
//            mDownloadThread.mHandler.sendMessage(message)
//        }
    }


    override fun onStart() {
        super.onStart()

        LocalBroadcastManager.getInstance(this).registerReceiver(
            receiver, IntentFilter(
                MUSIC_COMPLETE
            )
        )

        Intent(this@MainActivity, MusicPlayerService::class.java).also {
            bindService(it, serviceConnection, BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()

        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver)

        if (isBound) {
            unbindService(serviceConnection)
            isBound = false
        }
    }

    fun showProgressBar(shouldShow: Boolean) {
        binding.pbContent.isVisible = shouldShow
    }


    companion object {
        private const val TAG = "MyTags"
        const val MESSAGE_DATA = "message_data"
    }

    fun startMediaPlayer(v: View) {

        if (isBound) {

            if (service!!.isPlaying()) {
                service?.pause()
                binding.btPlay.text = "Play"
            } else {

                Intent(this@MainActivity, MusicPlayerService::class.java).also {
                    it.action = MUSIC_SERVICE_ACTION_START
                    startService(it)
                }
                service?.play()
                binding.btPlay.text = "Pause"
            }

        }

    }
}