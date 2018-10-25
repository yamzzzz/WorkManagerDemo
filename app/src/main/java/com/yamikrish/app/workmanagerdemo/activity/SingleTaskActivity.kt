package com.yamikrish.app.workmanagerdemo.activity

import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v7.app.AppCompatActivity
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.yamikrish.app.workmanagerdemo.worker.SingleWorker
import com.yamikrish.app.workmanagerdemo.R
import kotlinx.android.synthetic.main.activity_start_task.*


class SingleTaskActivity : AppCompatActivity() {

    private lateinit var workManager: WorkManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_start_task)

        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.single_task)
        }

        workManager = WorkManager.getInstance()

        start_single_task.setOnClickListener {
            val requestBuilder = OneTimeWorkRequest.Builder(SingleWorker::class.java).build()
            workManager.enqueue(requestBuilder)
        }
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