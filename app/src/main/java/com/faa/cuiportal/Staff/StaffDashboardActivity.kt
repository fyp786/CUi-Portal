package com.faa.cuiportal.Staff

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faa.cuiportal.R
import com.faa.cuiportal.ViewModel.RequestViewModel
import com.faa.cuiportal.ViewModel.RequestViewModelFactory
import com.faa.cuiportal.Retrofit.RetrofitInstance
import com.faa.cuiportal.Adapter.StaffRequestAdapter
import com.faa.cuiportal.Repository.RequestRepository

class StaffDashboardActivity : ComponentActivity() {
    private lateinit var email: String
    private lateinit var username: String
    private lateinit var requestRecyclerView: RecyclerView
    private val requestRepository = RequestRepository(RetrofitInstance.apiService)
    private val requestViewModel: RequestViewModel by viewModels {
        RequestViewModelFactory(requestRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staff_dashboard)

        email = intent.getStringExtra("EMAIL") ?: ""
        username = intent.getStringExtra("USERNAME") ?: ""

        val userNameTextView: TextView = findViewById(R.id.profileName)
        userNameTextView.text = username

        Toast.makeText(this, "Email: $email", Toast.LENGTH_LONG).show()

        // Initialize RecyclerView
        requestRecyclerView = findViewById(R.id.assignedTasksRecyclerView) // Ensure the correct ID
        requestRecyclerView.layoutManager = LinearLayoutManager(this)

        // Fetch complaints based on staff email
        fetchComplaints(email)

        // Observe the requests LiveData
        requestViewModel.requests.observe(this, Observer { tasks ->
            if (tasks.isEmpty()) {
                Toast.makeText(this, "No assigned tasks", Toast.LENGTH_SHORT).show()
            } else {
                val adapter = StaffRequestAdapter(tasks) { request ->
                    // Handle item clicks if needed
                }
                requestRecyclerView.adapter = adapter
            }
        })
    }

    private fun fetchComplaints(staffEmail: String) {
        // Fetch complaints for the given staff email
        requestViewModel.getComplaintsByStaffEmail(staffEmail)
    }
}
