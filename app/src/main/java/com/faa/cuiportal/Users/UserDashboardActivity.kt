package com.faa.cuiportal.Users

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.faa.cuiportal.R

class UserDashboardActivity : AppCompatActivity() {

    private lateinit var requestMaintenanceLayout: LinearLayout
    private lateinit var userId: String
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_dashboard)

        requestMaintenanceLayout = findViewById(R.id.requestmaintanace)

        userId = intent.getStringExtra("user_id") ?: "unknown_id"
        username = intent.getStringExtra("USERNAME") ?: ""

        val userNameTextView: TextView = findViewById(R.id.user_name)
        userNameTextView.text = username
        requestMaintenanceLayout.setOnClickListener {
            val intent = Intent(this@UserDashboardActivity, UserComplainActivity::class.java)

            startActivity(intent)
        }
    }
}
