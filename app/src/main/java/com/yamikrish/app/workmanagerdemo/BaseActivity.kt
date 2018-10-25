package com.yamikrish.app.workmanagerdemo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.yamikrish.app.workmanagerdemo.R
import com.yamikrish.app.workmanagerdemo.activity.ChainTaskActivity
import com.yamikrish.app.workmanagerdemo.activity.ConstraintsDataActivity
import com.yamikrish.app.workmanagerdemo.activity.PeriodicTaskActivity
import com.yamikrish.app.workmanagerdemo.activity.SingleTaskActivity
import kotlinx.android.synthetic.main.activity_main.*

class BaseActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_periodic_task -> startActivity(Intent(this@BaseActivity, PeriodicTaskActivity::class.java))

            R.id.btn_single_task -> startActivity(Intent(this@BaseActivity, SingleTaskActivity::class.java))

            R.id.btn_recurring_task -> startActivity(Intent(this@BaseActivity, ChainTaskActivity::class.java))

            R.id.btn_constraints_data -> startActivity(Intent(this@BaseActivity, ConstraintsDataActivity::class.java))


        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_periodic_task.setOnClickListener(this)
        btn_single_task.setOnClickListener(this)
        btn_recurring_task.setOnClickListener(this)
        btn_constraints_data.setOnClickListener(this)
    }
}