package com.faa.cuiportal.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.faa.cuiportal.Model.Request
import com.faa.cuiportal.R

class StaffRequestAdapter(
    private val requestList: List<Request>,
    private val onRequestClick: (Request) -> Unit
) : RecyclerView.Adapter<StaffRequestAdapter.RequestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_staff_assignedtask, parent, false)
        return RequestViewHolder(view)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        val request = requestList[position]
        holder.titleTextView.text = request.title
        holder.dateTextView.text = request.created_at

        holder.itemView.setOnClickListener {
            onRequestClick(request)
        }
    }

    override fun getItemCount(): Int = requestList.size

    class RequestViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.title)
        val dateTextView: TextView = view.findViewById(R.id.date)
    }
}
