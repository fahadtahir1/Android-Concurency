package com.brainstoriming.androidconcurency.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.brainstoriming.androidconcurency.R

class MusicPlayerService : Service() {

    private lateinit var mMediaPlayer: MediaPlayer

    private val localBinder = MusicPlayerBinder()

    override fun onCreate() {
        super.onCreate()
        mMediaPlayer = MediaPlayer.create(this, R.raw.my_song)

        mMediaPlayer.setOnCompletionListener {
            Intent(MUSIC_COMPLETE).also {
                it.putExtra(MESSAGE_KEY, "done")
                LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(it)

                stopForeground(true)
                stopSelf()
            }
        }
        Log.d(TAG, "onCreate: ")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand: ")

        when (intent?.action) {

            MUSIC_SERVICE_ACTION_PLAY -> {
                play()
            }
            MUSIC_SERVICE_ACTION_PAUSE -> {
                pause()
            }
            MUSIC_SERVICE_ACTION_START -> {
                makeServiceForegroundShowNotification()
            }
            MUSIC_SERVICE_ACTION_STOP -> {
                stopForeground(true)
                stopSelf()
            }

            else -> {
                stopSelf()
            }

        }

        return START_NOT_STICKY
    }

    private fun makeServiceForegroundShowNotification() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                "channel1",
                resources.getString(R.string.app_name),
                NotificationManager.IMPORTANCE_HIGH
            )

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }

        val playIntent = PendingIntent.getService(
            this,
            MUSIC_SERVICE_ACTION_PLAY_CODE,
            Intent(this, MusicPlayerService::class.java).setAction(MUSIC_SERVICE_ACTION_PLAY),
            0
        )


        val pauseIntent = PendingIntent.getService(
            this,
            MUSIC_SERVICE_ACTION_PAUSE_CODE,
            Intent(this, MusicPlayerService::class.java).setAction(MUSIC_SERVICE_ACTION_PAUSE),
            0
        )


        val stopIntent = PendingIntent.getService(
            this,
            MUSIC_SERVICE_ACTION_STOP_CODE,
            Intent(this, MusicPlayerService::class.java).setAction(MUSIC_SERVICE_ACTION_STOP),
            0
        )


        val notification = NotificationCompat.Builder(applicationContext, "channel1").also {
            it.setContentTitle(resources.getString(R.string.app_name))
            it.setContentText("This is a dummy song")
            it.setSmallIcon(R.drawable.ic_launcher_foreground)
            it.addAction(android.R.drawable.ic_media_pause, "Pause", pauseIntent)
            it.addAction(android.R.drawable.ic_media_play, "Play", playIntent)
            it.addAction(android.R.drawable.ic_menu_close_clear_cancel, "Stop", stopIntent)
        }.build()

        startForeground(123123, notification)
    }

    override fun onBind(intent: Intent?): IBinder {
        Log.d(TAG, "onBind: ")
        return localBinder
    }

    fun isPlaying() = mMediaPlayer.isPlaying

    fun play() = mMediaPlayer.start()

    fun pause() = mMediaPlayer.pause()

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        Log.d(TAG, "onRebind: ")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "onUnbind: ")
        return true
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

    companion object {
        private const val TAG = "MyTags"
        const val MUSIC_COMPLETE = "music_complete"
        const val MESSAGE_KEY = "message_key"


        const val MUSIC_SERVICE_ACTION_START = "com.brainstorming.androidconcurency.start"
        const val MUSIC_SERVICE_ACTION_PLAY = "com.brainstorming.androidconcurency.play"
        const val MUSIC_SERVICE_ACTION_PAUSE = "com.brainstorming.androidconcurency.pause"
        const val MUSIC_SERVICE_ACTION_STOP = "com.brainstorming.androidconcurency.STOP"

        const val MUSIC_SERVICE_ACTION_START_CODE = 100
        const val MUSIC_SERVICE_ACTION_PLAY_CODE = 101
        const val MUSIC_SERVICE_ACTION_PAUSE_CODE = 110
        const val MUSIC_SERVICE_ACTION_STOP_CODE = 111

    }
}