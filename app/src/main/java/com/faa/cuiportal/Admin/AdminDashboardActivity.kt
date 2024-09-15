package com.faa.cuiportal.Admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.faa.cuiportal.Activity.TotalStaffActivity
import com.faa.cuiportal.Model.Request
import com.faa.cuiportal.R
import com.faa.cuiportal.Retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminDashboardActivity : AppCompatActivity() {

    private lateinit var addstaff: Button
    private lateinit var assignTaskBtn: Button
    private lateinit var totalNewRequestsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        addstaff = findViewById(R.id.btn_add_staff)
        totalNewRequestsTextView = findViewById(R.id.total_newrequests)

        fetchNewRequests()

        addstaff.setOnClickListener {
            startActivity(Intent(this@AdminDashboardActivity, TotalStaffActivity::class.java))
        }

        assignTaskBtn = findViewById(R.id.btn_assign_task)
        assignTaskBtn.setOnClickListener {
            startActivity(Intent(this@AdminDashboardActivity, AdminNewRequestActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.new_requests).setOnClickListener {
            startActivity(Intent(this@AdminDashboardActivity, AdminNewRequestActivity::class.java))
        }
    }

    private fun fetchNewRequests() {
        val apiService = RetrofitInstance.apiService
        apiService.getNewRequests().enqueue(object : Callback<List<Request>> {
            override fun onResponse(call: Call<List<Request>>, response: Response<List<Request>>) {
                if (response.isSuccessful) {
                    val requests = response.body()
                    if (requests != null) {
                        val newRequestsCount = requests.filter { it.status == "new" }.size
                        totalNewRequestsTextView.text = newRequestsCount.toString()
                    }
                } else {
                    Log.e("AdminDashboard", "Failed to fetch new requests")
                }
            }

            override fun onFailure(call: Call<List<Request>>, t: Throwable) {
                Log.e("AdminDashboard", "Error fetching new requests: ${t.message}")
            }
        })
    }
}
