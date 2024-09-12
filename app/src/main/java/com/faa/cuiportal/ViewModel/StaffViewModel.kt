package com.faa.cuiportal.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.faa.cuiportal.Model.User
import com.faa.cuiportal.Repository.StaffRepository

class StaffViewModel : ViewModel() {

    private val repository = StaffRepository()

    val staffList: LiveData<List<User>>
        get() = repository.staffList

    fun loadStaffMembers() {
        repository.fetchStaffMembers()
    }
}
