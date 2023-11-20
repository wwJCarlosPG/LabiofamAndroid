package com.example.labiofam_android

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ServicesViewHolder(view: View):RecyclerView.ViewHolder(view) {
    private val service_name: TextView = view.findViewById(R.id.service_name)
    private val service_description: TextView = view.findViewById(R.id.service_description)
    private val service_price: TextView = view.findViewById(R.id.service_price)
    private val service_photo: ImageView = view.findViewById(R.id.service_photo)

    fun render(service: Service){
        service_name.text = service.name
        service_description.text = service.description
        service_price.text = service.price.toString()
        Glide.with(service_photo.context).load(service.photo).into(service_photo)

    }
}