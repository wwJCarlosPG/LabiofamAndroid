package com.example.labiofam_android

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ContactsViewHolder (view: View) : RecyclerView.ViewHolder(view){

    private val contact_name:TextView = view.findViewById(R.id.contact_name)
    private val contact_age:TextView = view.findViewById(R.id.contact_age)
    private val contact_position:TextView = view.findViewById(R.id.contact_position)
    private val contact_phone_number:TextView = view.findViewById(R.id.contact_phone_number)
    private val contact_photo:ImageView = view.findViewById(R.id.contact_photo)

    fun render(contact: Contact){
        contact_name.text = contact.name
        contact_age.text = contact.age
        contact_position.text = contact.position
        contact_phone_number.text = contact.phoneNumber.toString()
        Glide.with(contact_photo.context).load(contact.photo).into(contact_photo)

    }
}