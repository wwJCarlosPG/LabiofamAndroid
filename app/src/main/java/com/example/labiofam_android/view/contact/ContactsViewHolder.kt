package com.example.labiofam_android.view.contact

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.labiofam_android.R
import com.example.labiofam_android.apiModel.Contact

class ContactsViewHolder (view: View) : RecyclerView.ViewHolder(view){

    private val contact_name:TextView = view.findViewById(R.id.contact_name)
    private val contact_info:TextView = view.findViewById(R.id.contact_info)
    private val contact_occupation:TextView = view.findViewById(R.id.contact_occupation)
    private val contact_photo:ImageView = view.findViewById(R.id.contact_photo)

    fun render(contact: Contact){
        contact_name.text = contact.name
        contact_info.text = contact.info
        contact_occupation.text = contact.occupation
        Glide.with(contact_photo.context).load(contact.image).into(contact_photo)

    }
}