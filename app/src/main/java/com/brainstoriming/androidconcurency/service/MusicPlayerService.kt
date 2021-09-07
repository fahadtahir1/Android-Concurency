package com.brainstoriming.androidconcurency.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.brainstoriming.androidconcurency.R

class MusicPlayerService : Service() {

    private lateinit var mMediaPlayer : MediaPlayer

    private val localBinder = MusicPlayerBinder()

    override fun onCreate() {
        super.onCreate()
        mMediaPlayer = MediaPlayer.create(this, R.raw.maine_royaan)

        mMediaPlayer.setOnCompletionListener {
            Intent(MUSIC_COMPLETE).also{
                LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(it)
            }
        }
        Log.d(TAG, "onCreate: ")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand: ")
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder {
        Log.d(TAG, "onBind: ")
        return localBinder
    }

    fun isPlaying() = mMediaPlayer.isPlaying

    fun play() = mMediaPlayer.start()

    fun pause() = mMediaPlayer.pause()

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "onUnbind: ")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        mMediaPlayer.release()
        Log.d(TAG, "onDestroy: ")
    }


    inner class MusicPlayerBinder : Binder() {
        internal val service: MusicPlayerService
            get() = this@MusicPlayerService
    }

    companion object{
        private const val TAG = "MyTags"
        const val MUSIC_COMPLETE = "music_complete"
    }
}