package com.yamikrish.app.workmanagerdemo.activity

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import androidx.work.*
import com.yamikrish.app.workmanagerdemo.worker.periodicWorker.NotifyWorker
import kotlinx.android.synthetic.main.activity_periodic_notifier.*
import java.util.concurrent.TimeUnit
import com.yamikrish.app.workmanagerdemo.R
import com.yamikrish.app.workmanagerdemo.worker.periodicWorker.NotifyInitialWorker

class PeriodicTaskActivity : AppCompatActivity() {

    private lateinit var workManager: WorkManager
    private val workTag = "starterNotifiWork"
    private val periodicWorkTag = "periodicNotifiWork"

    private lateinit var prefs: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private val NOTIFICATION_STATUS = "notification_status"
    private lateinit var requestBuilder: OneTimeWorkRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_periodic_notifier)

        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.single_task)
        }

        prefs = getPreferences(Context.MODE_PRIVATE)
        editor = prefs.edit()

        workManager = WorkManager.getInstance()


        remindSwitch.isChecked = prefs.getBoolean(NOTIFICATION_STATUS, false)

        /*remindSwitch.setOnClickListener {
            if (remindSwitch.isChecked) {
                val requestBuilder = PeriodicWorkRequest.Builder(NotifyWorker::class.java, 15, TimeUnit.MINUTES)
                workManager.enqueueUniquePeriodicWork(workTag, ExistingPeriodicWorkPolicy.REPLACE, requestBuilder.build())
            } else {
                workManager.cancelUniqueWork(workTag)
            }
            editor.putBoolean(NOTIFICATION_STATUS, remindSwitch.isChecked)
            editor.apply()
        }*/



        remindSwitch.setOnClickListener {
            if (remindSwitch.isChecked) {
                requestBuilder = OneTimeWorkRequest.Builder(NotifyInitialWorker::class.java)
                        .setInitialDelay(1, TimeUnit.MINUTES)
                        .build()
                workManager.getStatusByIdLiveData(requestBuilder.id).observe(this@PeriodicTaskActivity, android.arch.lifecycle.Observer { workerStatus ->
                    if (workerStatus != null && workerStatus.state.isFinished) {
                        startNotifyWorker()
                    }

                })
                workManager.beginUniqueWork(workTag, ExistingWorkPolicy.REPLACE, requestBuilder).enqueue()
            } else {
                workManager.cancelAllWork()
            }
            editor.putBoolean(NOTIFICATION_STATUS, remindSwitch.isChecked)
            editor.apply()
        }

    }

    private fun startNotifyWorker() {
        val requestBuilder = PeriodicWorkRequest.Builder(NotifyWorker::class.java, 15, TimeUnit.MINUTES)
        WorkManager.getInstance().enqueueUniquePeriodicWork(periodicWorkTag, ExistingPeriodicWorkPolicy.REPLACE, requestBuilder.build())
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}
