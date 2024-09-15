package com.faa.cuiportal.Activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.faa.cuiportal.Adapter.StaffAdapter
import com.faa.cuiportal.Admin.AdminNewRequestActivity
import com.faa.cuiportal.Model.User
import com.faa.cuiportal.ViewModel.StaffViewModel
import com.faa.cuiportal.databinding.ActivityTotalStaffBinding

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

        // Show a Toast message indicating success
        Toast.makeText(this, "Assigned to $assignedStaffUsername", Toast.LENGTH_SHORT).show()

        // After assigning staff, navigate to AdminNewRequestActivity
        val intent = Intent(this, AdminNewRequestActivity::class.java).apply {
            putExtra("ASSIGNED_STAFF_USERNAME", assignedStaffUsername)
            putExtra("REQUEST_ID", requestId)
        }
        startActivity(intent)
        finish()
    }
}
