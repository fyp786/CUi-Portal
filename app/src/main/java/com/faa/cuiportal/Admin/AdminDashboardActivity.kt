package com.faa.cuiportal.Admin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.faa.cuiportal.Activity.TotalStaffActivity
import com.faa.cuiportal.R

class AdminDashboardActivity : AppCompatActivity() {

    private lateinit var addstaff: Button
    private lateinit var assignTaskBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)
        addstaff = findViewById(R.id.btn_add_staff)
        addstaff.setOnClickListener {
            startActivity(Intent(this@AdminDashboardActivity, TotalStaffActivity::class.java))
        }
        assignTaskBtn = findViewById(R.id.btn_assign_task)
        assignTaskBtn.setOnClickListener {
            startActivity(Intent(this@AdminDashboardActivity, AdminNewRequestActivity::class.java))
        }
        findViewById<LinearLayout>(R.id.new_requests).setOnClickListener {
            startActivity(Intent(this@AdminDashboardActivity, AdminNewRequestActivity::class.java))
        }

    }
}