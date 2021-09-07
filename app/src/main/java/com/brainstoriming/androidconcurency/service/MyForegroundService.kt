package com.brainstoriming.androidconcurency.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.brainstoriming.androidconcurency.R

class MyForegroundService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        showNotification()

        Thread(Runnable {
            Log.d(TAG, "onStartCommand: Service Started")
            var count = 0
            while (count < 11) {
                Log.d(TAG, "onStartCommand: Service is in progress $count")
                Thread.sleep(2000)
                count++
            }

            Log.d(TAG, "onStartCommand: Service Completed")

            stopForeground(true)
            stopSelf()

        }).start()

        return START_STICKY
    }

    private fun showNotification() {


        if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.O) {

            val notificationChannel = NotificationChannel(
                "channel1",
                resources.getString(R.string.app_name),
                NotificationManager.IMPORTANCE_DEFAULT
            ).also {
                it.description = "This is channel notification description"
            }

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(notificationChannel)

        }

        val notification: Notification = NotificationCompat.Builder(applicationContext, "channel1").also {
            it.setContentTitle(resources.getString(R.string.app_name))
            it.setContentText("This is short description text")
            it.setSmallIcon(R.drawable.ic_launcher_foreground)
            it.setOngoing(true)
        }.build()

        startForeground(324, notification)
    }

    override fun onBind(intent: Intent): Nothing? = null

    companion object {
        private const val TAG = "MyTags"
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }
}