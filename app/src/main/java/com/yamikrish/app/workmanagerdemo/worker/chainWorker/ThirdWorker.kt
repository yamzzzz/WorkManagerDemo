package com.yamikrish.app.workmanagerdemo.worker.chainWorker

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.support.v4.app.NotificationCompat
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters


class ThirdWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    val mContext = context

    override fun doWork(): ListenableWorker.Result {
        sendNotification()
        return ListenableWorker.Result.SUCCESS
    }

    fun sendNotification() {
        val notificationManager = mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "WorkManager_04"

        //If on Oreo then notification required a notification channel.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "WorkManager", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(mContext, channelId)
                .setContentTitle("WorkManager")
                .setContentText("Message from Third Worker!!")
                .setSmallIcon(R.drawable.ic_lock_idle_alarm)

        notificationManager.notify(5, notification.build())
    }

}