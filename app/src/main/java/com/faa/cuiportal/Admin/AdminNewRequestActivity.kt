package com.faa.cuiportal.Admin
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faa.cuiportal.R
import com.faa.cuiportal.Repository.RequestRepository
import com.faa.cuiportal.Retrofit.RetrofitInstance  // Make sure to import RetrofitInstance
import com.faa.cuiportal.ViewModel.RequestViewModel
import com.faa.cuiportal.ViewModel.RequestViewModelFactory
import com.faa.cuiportal.Adapter.RequestAdapter

class AdminNewRequestActivity : AppCompatActivity() {

    private lateinit var requestViewModel: RequestViewModel
    private lateinit var requestAdapter: RequestAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_new_request)

        val apiService = RetrofitInstance.apiService  // Get ApiService from RetrofitInstance
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


        // Fetch new requests
        requestViewModel.fetchNewRequests()
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view)
        requestAdapter = RequestAdapter()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@AdminNewRequestActivity)
            adapter = requestAdapter
        }
    }
}
