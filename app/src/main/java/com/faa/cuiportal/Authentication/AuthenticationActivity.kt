package com.faa.cuiportal.Authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.faa.cuiportal.R

class AuthenticationActivity : AppCompatActivity() {
    private lateinit var backButton: ImageView
    private lateinit var loginBtn: Button
    private lateinit var SignupBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        backButton = findViewById(R.id.back_button)
        loginBtn = findViewById(R.id.login_button)
        SignupBtn = findViewById(R.id.signup_button)

        backButton.setOnClickListener {
            startActivity(Intent(this@AuthenticationActivity, WelcomeActivity::class.java))
            finishAffinity()
        }

        loginBtn.setOnClickListener {
            startActivity(Intent(this@AuthenticationActivity, LoginActivity::class.java))
        }

        SignupBtn.setOnClickListener {
            startActivity(Intent(this@AuthenticationActivity, SignUpActivity::class.java))
        }
    }
}
