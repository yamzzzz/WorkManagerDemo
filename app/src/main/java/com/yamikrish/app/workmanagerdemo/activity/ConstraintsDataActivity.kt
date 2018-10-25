package com.yamikrish.app.workmanagerdemo.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.yamikrish.app.workmanagerdemo.utils.Constants
import com.yamikrish.app.workmanagerdemo.worker.constraintDataWorker.ConstraintWorker
import kotlinx.android.synthetic.main.activity_start_task.*
import com.yamikrish.app.workmanagerdemo.R


class ConstraintsDataActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_task)

        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.single_task)
        }

        val constraints = Constraints.Builder().setRequiresCharging(true).build()

        val data = Data.Builder()
                .putString(Constants.EXTRA_TITLE, "Message!!")
                .putString(Constants.EXTRA_MESSAGE, "Message sent from Activity")
                .build()

        start_single_task.setOnClickListener {
            val requestBuilder = OneTimeWorkRequest.Builder(ConstraintWorker::class.java)
                    .setConstraints(constraints)
                    .setInputData(data)
                    .build()
            WorkManager.getInstance().enqueue(requestBuilder)

            WorkManager.getInstance().getStatusByIdLiveData(requestBuilder.id).observe(this@ConstraintsDataActivity, android.arch.lifecycle.Observer { workerStatus ->
                if (workerStatus != null && workerStatus.state.isFinished) {
                    Toast.makeText(this@ConstraintsDataActivity, workerStatus.outputData.getString(Constants.EXTRA_OUTPUT_MESSAGE), Toast.LENGTH_SHORT).show()
                }

            })
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
