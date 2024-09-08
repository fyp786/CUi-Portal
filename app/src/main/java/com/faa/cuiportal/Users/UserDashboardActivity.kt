package com.faa.cuiportal.Users

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
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

        userId = intent.getStringExtra("USER_ID") ?: ""
        username = intent.getStringExtra("USERNAME") ?: ""

        requestMaintenanceLayout.setOnClickListener {
            val intent = Intent(this@UserDashboardActivity, UserComplainActivity::class.java).apply {
                putExtra("USER_ID", userId)
                putExtra("USERNAME", username)
            }
            startActivity(intent)
        }
    }
}
