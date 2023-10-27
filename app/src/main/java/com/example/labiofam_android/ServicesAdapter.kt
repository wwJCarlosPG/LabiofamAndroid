package com.example.labiofam_android

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class ServicesAdapter(private val services: List<Service>): RecyclerView.Adapter<ServicesViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_service, parent, false)
        return ServicesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServicesViewHolder, position: Int) {
        holder.render(services[position])
    }

    override fun getItemCount(): Int = services.size
}
