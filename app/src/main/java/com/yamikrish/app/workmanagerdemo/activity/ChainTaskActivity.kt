package com.yamikrish.app.workmanagerdemo.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.yamikrish.app.workmanagerdemo.worker.chainWorker.FirstWorker
import com.yamikrish.app.workmanagerdemo.worker.chainWorker.SecondWorker
import com.yamikrish.app.workmanagerdemo.worker.chainWorker.ThirdWorker
import kotlinx.android.synthetic.main.activity_start_task.*
import com.yamikrish.app.workmanagerdemo.R
import java.util.concurrent.TimeUnit


class ChainTaskActivity : AppCompatActivity() {

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
            val request1 = OneTimeWorkRequest.Builder(FirstWorker::class.java).build()
            val request2 = OneTimeWorkRequest.Builder(SecondWorker::class.java).setInitialDelay(30, TimeUnit.SECONDS).build()
            val request3 = OneTimeWorkRequest.Builder(ThirdWorker::class.java).setInitialDelay(30, TimeUnit.SECONDS).build()

            // workManager.beginWith(request1).then(request2).then(request3).enqueue() // Normal Chain request
            workManager.beginWith(request1).then(request2, request3).enqueue() // Parallel request
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