package com.yamikrish.app.workmanagerdemo.worker.periodicWorker

import android.util.Log
import androidx.work.Worker
import android.support.v4.app.NotificationCompat
import android.app.NotificationManager
import android.app.NotificationChannel
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.R;
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import android.support.annotation.NonNull


class NotifyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    val mContext = context

    override fun doWork(): ListenableWorker.Result {
        sendNotification()
        return ListenableWorker.Result.SUCCESS
    }

    fun sendNotification() {
        val notificationManager = mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "WorkManager_01"

        //If on Oreo then notification required a notification channel.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "WorkManager", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(mContext, channelId)
                .setContentTitle("Timer Reminder")
                .setContentText("It's been 15 minutes!!!")
                .setSmallIcon(R.drawable.ic_lock_idle_alarm)

        notificationManager.notify(2, notification.build())
    }
}
