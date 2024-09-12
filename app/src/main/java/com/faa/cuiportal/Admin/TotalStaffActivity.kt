package com.faa.cuiportal.Activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.faa.cuiportal.Adapter.StaffAdapter
import com.faa.cuiportal.ViewModel.StaffViewModel
import com.faa.cuiportal.databinding.ActivityTotalStaffBinding

class TotalStaffActivity : AppCompatActivity() {

    private val staffViewModel: StaffViewModel by viewModels()
    private lateinit var binding: ActivityTotalStaffBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTotalStaffBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.staffRecyclerView.layoutManager = LinearLayoutManager(this)

        staffViewModel.staffList.observe(this, Observer { staffList ->
            if (staffList.isEmpty()) {
                binding.staffRecyclerView.visibility = View.GONE
            } else {
                binding.staffRecyclerView.adapter = StaffAdapter(staffList)
                binding.staffRecyclerView.visibility = View.VISIBLE
            }
        })

        staffViewModel.loadStaffMembers()
    }
}
