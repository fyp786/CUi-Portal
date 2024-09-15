package com.faa.cuiportal.Admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
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
import com.faa.cuiportal.Model.Request

class AdminNewRequestActivity : AppCompatActivity() {

    private lateinit var requestViewModel: RequestViewModel
    private lateinit var requestAdapter: RequestAdapter
    private lateinit var recyclerView: RecyclerView

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
            // Handle error (show a Toast or Snackbar)
        })
        findViewById<ImageButton>(R.id.back_button).setOnClickListener {
            finish()
        }

        // Fetch new requests
        requestViewModel.fetchNewRequests()
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view)
        requestAdapter = RequestAdapter { request ->
            val intent = Intent(this, AdminRequestDetailActivity::class.java)
            intent.putExtra("REQUEST_ID", request.id) // Pass request ID or entire object as needed
            startActivity(intent)
        }
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@AdminNewRequestActivity)
            adapter = requestAdapter
        }
    }

}
