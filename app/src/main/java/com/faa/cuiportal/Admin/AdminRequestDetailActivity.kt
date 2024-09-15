package com.faa.cuiportal.Admin
// AdminRequestDetailActivity.kt

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.faa.cuiportal.Activity.TotalStaffActivity
import com.faa.cuiportal.R
import com.faa.cuiportal.Model.Request
import com.faa.cuiportal.ViewModel.RequestViewModel
import com.faa.cuiportal.ViewModel.RequestViewModelFactory
import com.faa.cuiportal.Repository.RequestRepository
import com.faa.cuiportal.Retrofit.RetrofitInstance

class AdminRequestDetailActivity : AppCompatActivity() {

    private lateinit var requestViewModel: RequestViewModel

    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var usernameTextView: TextView
    private lateinit var createdAtTextView: TextView
    private lateinit var assignButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_request_detail)

        val apiService = RetrofitInstance.apiService
        val requestRepository = RequestRepository(apiService)
        val factory = RequestViewModelFactory(requestRepository)
        requestViewModel = ViewModelProvider(this, factory)[RequestViewModel::class.java]

        titleTextView = findViewById(R.id.tvTitle)
        descriptionTextView = findViewById(R.id.description)
        usernameTextView = findViewById(R.id.tvAuthor)
        createdAtTextView = findViewById(R.id.tvDueDate)
        assignButton = findViewById(R.id.btnAssign)

        val requestId = intent.getIntExtra("REQUEST_ID", -1)
        if (requestId != -1) {
            requestViewModel.fetchNewRequests()
        }

        requestViewModel.requests.observe(this, Observer { requests ->
            val request = requests.find { it.id == requestId }
            if (request != null) {
                displayRequestDetails(request)
            }
        })

        assignButton.setOnClickListener {
            val intent = Intent(this, TotalStaffActivity::class.java)
            intent.putExtra("REQUEST_ID", requestId)
            startActivityForResult(intent, REQUEST_CODE_ASSIGN)
        }
    }

    private fun displayRequestDetails(request: Request) {
        titleTextView.text = request.title
        descriptionTextView.text = request.description
        usernameTextView.text = request.username
        createdAtTextView.text = request.created_at
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ASSIGN && resultCode == RESULT_OK) {
            val assignedStaffUsername = data?.getStringExtra("ASSIGNED_STAFF_USERNAME")
            Toast.makeText(this, "Assigned to $assignedStaffUsername", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val REQUEST_CODE_ASSIGN = 1
    }
}
