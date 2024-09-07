package com.faa.cuiportal.Authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.faa.cuiportal.R
import com.faa.cuiportal.Staff.StaffDashboardActivity
import com.faa.cuiportal.Users.UserDashboardActivity
import com.faa.cuiportal.ViewModel.SignUpViewModel

class SignUpActivity : AppCompatActivity() {

    private lateinit var signUpViewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signUpViewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        val spinner: Spinner = findViewById(R.id.spinner_usertype)
        val username: EditText = findViewById(R.id.et_username)
        val email: EditText = findViewById(R.id.et_email)
        val password: EditText = findViewById(R.id.et_password)
        val confirmPassword: EditText = findViewById(R.id.et_confirm_password)
        val signUpButton: Button = findViewById(R.id.btn_sign_up)

        signUpButton.setOnClickListener {
            val selectedType = spinner.selectedItem.toString()
            val userType = if (selectedType == "user" || selectedType == "staff") selectedType else null

            if (userType != null && password.text.toString() == confirmPassword.text.toString()) {
                signUpViewModel.signUp(
                    username.text.toString(),
                    email.text.toString(),
                    password.text.toString(),
                    userType
                )
                signUpViewModel.response.observe(this) { response ->
                    if (response.message == "Success") {
                        when (userType) {
                            "user" -> {
                                startActivity(Intent(this, UserDashboardActivity::class.java))
                                finish()
                            }
                            "staff" -> {
                                startActivity(Intent(this, StaffDashboardActivity::class.java))
                                finish()
                            }
                        }
                    } else {
                        Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please fill all fields correctly", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
