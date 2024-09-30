package com.faa.cuiportal.Activity


import android.content.Intent
import retrofit2.Call
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.faa.cuiportal.Adapter.StaffAdapter
import com.faa.cuiportal.Admin.AdminNewRequestActivity
import com.faa.cuiportal.Model.ApiResponse
import com.faa.cuiportal.Model.User
import com.faa.cuiportal.Retrofit.RetrofitInstance
import com.faa.cuiportal.ViewModel.StaffViewModel
import com.faa.cuiportal.databinding.ActivityTotalStaffBinding
import retrofit2.Callback
import retrofit2.Response

class TotalStaffActivity : AppCompatActivity() {

    private val staffViewModel: StaffViewModel by viewModels()
    private lateinit var binding: ActivityTotalStaffBinding
    private var requestId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTotalStaffBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestId = intent.getIntExtra("REQUEST_ID", -1)

        binding.staffRecyclerView.layoutManager = LinearLayoutManager(this)

        staffViewModel.staffList.observe(this, Observer { staffList ->
            if (staffList.isEmpty()) {
                binding.staffRecyclerView.visibility = View.GONE
            } else {
                binding.staffRecyclerView.adapter = StaffAdapter(staffList) { staff ->
                    onStaffSelected(staff)
                }
                binding.staffRecyclerView.visibility = View.VISIBLE
            }
        })

        staffViewModel.loadStaffMembers()
    }

    private fun onStaffSelected(staff: User) {
        val assignedStaffUsername = staff.username
        val assignedStaffId = staff.id
        val assignedStaffEmail = staff.email // Get the selected staff's email

        Toast.makeText(this, "Assigning to $assignedStaffUsername", Toast.LENGTH_SHORT).show()

        assignStaffToRequest(requestId, assignedStaffId, assignedStaffEmail)
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
                        Toast.makeText(this@TotalStaffActivity, "Staff assigned successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@TotalStaffActivity, AdminNewRequestActivity::class.java)
                        intent.putExtra("REQUEST_ID", requestId)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@TotalStaffActivity, "Error: ${apiResponse?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Toast.makeText(this@TotalStaffActivity, "Failed to assign staff", Toast.LENGTH_SHORT).show()
            }
        })
    }


}
