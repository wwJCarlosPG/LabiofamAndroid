package com.example.labiofam_android

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ContactsCategoriesAdapter(private val categories: List<ContactsCategories>):
    RecyclerView.Adapter<ContactsCategoriesViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactsCategoriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contacts_categories, parent, false)
        return ContactsCategoriesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactsCategoriesViewHolder, position: Int) {
        holder.render(categories[position])
    }

    override fun getItemCount(): Int = categories.size
}
