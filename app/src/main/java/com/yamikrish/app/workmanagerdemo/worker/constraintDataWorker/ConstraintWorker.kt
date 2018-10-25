package com.yamikrish.app.workmanagerdemo.worker.constraintDataWorker

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.support.v4.app.NotificationCompat
import androidx.work.Data
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.yamikrish.app.workmanagerdemo.utils.Constants
import com.yamikrish.app.workmanagerdemo.utils.Constants.Companion.EXTRA_OUTPUT_MESSAGE

class ConstraintWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    val mContext = context
    var title: String? = ""
    var message: String? = ""

    override fun doWork(): ListenableWorker.Result {
        title = inputData.getString(Constants.EXTRA_TITLE)
        message = inputData.getString(Constants.EXTRA_MESSAGE)

        sendNotification()

        val output = Data.Builder()
                .putString(EXTRA_OUTPUT_MESSAGE, "Message from ConstraintWorker!")
                .build()

        outputData = output
        return ListenableWorker.Result.SUCCESS
    }

    fun sendNotification() {
        val notificationManager = mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "WorkManager_05"

        //If on Oreo then notification required a notification channel.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "WorkManager", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(mContext, channelId)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_lock_idle_alarm)

        notificationManager.notify(6, notification.build())
    }

}