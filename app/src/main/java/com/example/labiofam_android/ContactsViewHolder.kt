package com.example.labiofam_android

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class ContactsViewHolder (view: View) : RecyclerView.ViewHolder(view){

    private val contact_name:TextView = view.findViewById(R.id.contact_name)
    private val contact_age:TextView = view.findViewById(R.id.contact_age)
    private val contact_position:TextView = view.findViewById(R.id.contact_position)
    private val image_contact_phone_cv:CardView = view.findViewById(R.id.image_contact_phone_cv)

    fun render(contact: Contact){
        contact_name.text = contact.name
        contact_age.text = contact.age
        contact_position.text = contact.position

        val phoneNumber = contact.phoneNumber
    }
}