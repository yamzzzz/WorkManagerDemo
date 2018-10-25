package com.yamikrish.app.workmanagerdemo.worker.periodicWorker

import android.content.Context
import androidx.work.*

class NotifyInitialWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): ListenableWorker.Result {
        return ListenableWorker.Result.SUCCESS
    }

}
