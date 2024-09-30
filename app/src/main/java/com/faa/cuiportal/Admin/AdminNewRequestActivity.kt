package com.faa.cuiportal.Admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faa.cuiportal.R
import com.faa.cuiportal.Repository.RequestRepository
import com.faa.cuiportal.Retrofit.RetrofitInstance
import com.faa.cuiportal.ViewModel.RequestViewModel
import com.faa.cuiportal.ViewModel.RequestViewModelFactory
import com.faa.cuiportal.Adapter.RequestAdapter
import com.faa.cuiportal.Model.ApiResponse
import com.faa.cuiportal.Model.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminNewRequestActivity : AppCompatActivity() {

    private lateinit var requestViewModel: RequestViewModel
    private lateinit var requestAdapter: RequestAdapter
    private lateinit var recyclerView: RecyclerView
    private var requestId: Int = -1
    private var staffId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_new_request)

        val apiService = RetrofitInstance.apiService
        val requestRepository = RequestRepository(apiService)

        val factory = RequestViewModelFactory(requestRepository)
        requestViewModel = ViewModelProvider(this, factory)[RequestViewModel::class.java]

        setupRecyclerView()

        requestViewModel.requests.observe(this, Observer { requests ->
            Log.d("AdminNewRequestActivity", "Requests: $requests")
            requestAdapter.submitList(requests)
        })

        requestViewModel.error.observe(this, Observer { errorMessage ->
            Log.e("AdminNewRequestActivity", "Error: $errorMessage")
        })

        findViewById<ImageButton>(R.id.back_button).setOnClickListener {
            finish()
        }

        requestViewModel.fetchNewRequests()

        handleIncomingIntent()

    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view)
        requestAdapter = RequestAdapter { request ->
            val intent = Intent(this, AdminRequestDetailActivity::class.java)
            intent.putExtra("REQUEST_ID", request.id)
            startActivity(intent)
        }
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@AdminNewRequestActivity)
            adapter = requestAdapter
        }
    }

    private fun handleIncomingIntent() {
        intent?.let {
            requestId = it.getIntExtra("REQUEST_ID", -1)
            staffId = it.getIntExtra("ASSIGNED_STAFF_ID", -1)
            val staffEmail = it.getStringExtra("ASSIGNED_STAFF_EMAIL")
            if (requestId != -1 && staffId != -1 && staffEmail != null) {
                assignStaffToRequest(requestId, staffId, staffEmail)
            } else {
            }
        }
    }


    private fun assignStaffToRequest(requestId: Int, staffId: Int, staffEmail: String) {
        val apiService = RetrofitInstance.apiService
        val call = apiService.assignStaff(
            id = requestId,
            staffId = staffId,
            staffEmail = staffEmail
        )

        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse?.status == "success") {
                        Toast.makeText(this@AdminNewRequestActivity, "Staff assigned successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@AdminNewRequestActivity, "Error: ${apiResponse?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Toast.makeText(this@AdminNewRequestActivity, "Failed to assign staff", Toast.LENGTH_SHORT).show()
            }
        })
    }

}
