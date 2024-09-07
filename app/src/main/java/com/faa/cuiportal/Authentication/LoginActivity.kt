package com.faa.cuiportal.Authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.faa.cuiportal.R
import com.faa.cuiportal.Staff.StaffDashboardActivity
import com.faa.cuiportal.Users.UserDashboardActivity
import com.faa.cuiportal.ViewModel.SignInViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var signInViewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signInViewModel = ViewModelProvider(this).get(SignInViewModel::class.java)

        val email: EditText = findViewById(R.id.et_email)
        val password: EditText = findViewById(R.id.et_password)
        val signInButton: Button = findViewById(R.id.btn_sign_in)

        signInButton.setOnClickListener {
            val enteredEmail = email.text.toString().trim() // Ensure no extra spaces
            val enteredPassword = password.text.toString().trim() // Ensure no extra spaces

            if (enteredEmail.isNotEmpty() && enteredPassword.isNotEmpty()) {
                signInViewModel.login(enteredEmail, enteredPassword)

                signInViewModel.response.observe(this) { response ->
                    when (response.message) {
                        "user" -> {
                            startActivity(Intent(this, UserDashboardActivity::class.java))
                            finish()
                        }
                        "staff" -> {
                            startActivity(Intent(this, StaffDashboardActivity::class.java))
                            finish()
                        }
                        else -> {
                            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Please fill in both email and password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
