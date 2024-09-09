package com.faa.cuiportal.Users

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.faa.cuiportal.R
import com.faa.cuiportal.ViewModel.ComplaintsViewModel

class UserComplainActivity : AppCompatActivity() {

    private val complaintsViewModel: ComplaintsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_complain)

        val title: EditText = findViewById(R.id.input_title)
        val description: EditText = findViewById(R.id.input_description)
        val locationSpinner: Spinner = findViewById(R.id.spinner_location)
        val roomNumber: EditText = findViewById(R.id.input_room_number)
        val submitButton: Button = findViewById(R.id.submit_btn)
        val cancelButton: Button = findViewById(R.id.cancel_btn)

        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "unknown_user") ?: "unknown_user"
        val userId = sharedPreferences.getString("user_id", "unknown_id") ?: "unknown_id"

        Log.d("UserComplainActivity", "Retrieved username: $username")
        Log.d("UserComplainActivity", "Retrieved userId: $userId")

        ArrayAdapter.createFromResource(
            this,
            R.array.select_location,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            locationSpinner.adapter = adapter
        }

        submitButton.setOnClickListener {
            val selectedLocation = locationSpinner.selectedItem.toString()
            val enteredTitle = title.text.toString().trim()
            val enteredDescription = description.text.toString().trim()
            val enteredRoomNumber = roomNumber.text.toString().trim()

            if (enteredTitle.isNotEmpty() && enteredDescription.isNotEmpty() &&
                selectedLocation != "Select Location" && enteredRoomNumber.isNotEmpty()
            ) {
                complaintsViewModel.newRequest(
                    enteredTitle,
                    enteredDescription,
                    selectedLocation,
                    enteredRoomNumber,
                    username
                )
                complaintsViewModel.response.observe(this) { response ->
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                    if (response.message.contains("successfully", ignoreCase = true)) {
                       finish()
                    }
                }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        cancelButton.setOnClickListener {
            finish()
        }
    }
}
