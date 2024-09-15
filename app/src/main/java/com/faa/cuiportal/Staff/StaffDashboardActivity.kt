package com.faa.cuiportal.Staff

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.faa.cuiportal.R

class StaffDashboardActivity : AppCompatActivity() {
    private lateinit var userId: String
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staff_dashboard)

        userId = intent.getStringExtra("user_id") ?: "unknown_id"
        username = intent.getStringExtra("USERNAME") ?: ""
        val userNameTextView: TextView = findViewById(R.id.profileName)
        userNameTextView.text = username

    }
}