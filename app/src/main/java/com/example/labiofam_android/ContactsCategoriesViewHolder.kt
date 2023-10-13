package com.example.labiofam_android

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactsCategoriesViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val tvContactsCategoryName:TextView = view.findViewById(R.id.contacts_categories_name)
    private val divider:View = view.findViewById(R.id.divider)
    fun render(contactsCategories: ContactsCategories){

        when(contactsCategories){
            ContactsCategories.Comercial -> {
                tvContactsCategoryName.text = "Comercial"
            }
            ContactsCategories.CorreoInstitucional -> {
                tvContactsCategoryName.text = "Correo Institucional"
            }
            ContactsCategories.Other -> {
                tvContactsCategoryName.text = "Otros"
            }
        }
    }
}