package com.faa.cuiportal.Authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.faa.cuiportal.Admin.AdminDashboardActivity
import com.faa.cuiportal.R
import com.faa.cuiportal.Staff.StaffDashboardActivity
import com.faa.cuiportal.Users.UserDashboardActivity
import com.faa.cuiportal.ViewModel.SignInViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var backButton: ImageView
    private lateinit var createNewAccount: TextView

    private lateinit var signInViewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signInViewModel = ViewModelProvider(this).get(SignInViewModel::class.java)

        val email: EditText = findViewById(R.id.et_email)
        val password: EditText = findViewById(R.id.et_password)
        val signInButton: Button = findViewById(R.id.btn_sign_in)
        createNewAccount = findViewById(R.id.tv_sign_up)
        backButton = findViewById(R.id.back_button)

        backButton.setOnClickListener {
            startActivity(Intent(this@LoginActivity, AuthenticationActivity::class.java))
        }

        createNewAccount.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
        }

        signInButton.setOnClickListener {
            val enteredEmail = email.text.toString().trim()
            val enteredPassword = password.text.toString().trim()

            if (enteredEmail.isNotEmpty() && enteredPassword.isNotEmpty()) {
                signInViewModel.login(enteredEmail, enteredPassword)
            } else {
                Toast.makeText(this, "Please fill in both email and password", Toast.LENGTH_SHORT).show()
            }
        }

        // Observe the response once, outside of the click listener
        signInViewModel.response.observe(this) { response ->
            if (response.user_type != null) {
                val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("username", response.user?.username) // Save username
                editor.putString("user_id", response.user?.userId) // Save user ID
                editor.apply()

                val intent = Intent(this, when (response.user_type) {
                    "user" -> UserDashboardActivity::class.java
                    "staff" -> StaffDashboardActivity::class.java
                    "admin" -> AdminDashboardActivity::class.java
                    else -> null
                })

                if (intent != null) {
                    intent.putExtra("USER_ID", response.user?.userId)
                    intent.putExtra("USERNAME", response.user?.username)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
